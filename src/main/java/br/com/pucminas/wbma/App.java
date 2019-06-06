package br.com.pucminas.wbma;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToLongFunction;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffEntry.ChangeType;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;

import br.com.pucminas.wbma.dao.CommitDAO;
import br.com.pucminas.wbma.dao.ContributorDAO;
import br.com.pucminas.wbma.dao.RepositoryDAO;
import br.com.pucminas.wbma.dao.RepositoryXContributorDAO;
import br.com.pucminas.wbma.dtos.CommitDTO;
import br.com.pucminas.wbma.dtos.DiffDTO;
import br.com.pucminas.wbma.entity.Commit;
import br.com.pucminas.wbma.entity.Contributor;
import br.com.pucminas.wbma.entity.RepositoryXContributor;
import br.com.pucminas.wbma.entity.RepositoryXContributorId;
import br.com.pucminas.wbma.enums.Semester;

public class App {

	private static final Logger LOGGER = Logger.getLogger(App.class.getName());

	private static final String GIT_EXTENSION = ".git";
	private static final String REPOSITORIES_FOLDER = "repos/";

	/**
	 * 
	 * @param args - the first arg (args[0]) <b>MUST</b> be the order of the
	 *             semester
	 * 
	 * @see Semester
	 */
	public static void main(String... args) {

		var order = Integer.parseInt(args[0]);

		var contributorDAO = new ContributorDAO();
		var repositoryXContributorDAO = new RepositoryXContributorDAO();
		var commitDAO = new CommitDAO();
		var repositories = new RepositoryDAO()
				.findAllBySemester(Semester.getSemesterByOrder(order).orElseThrow().getDateSemester());

		repositories.parallelStream().map(repo -> {
			File folder = new File(REPOSITORIES_FOLDER + repo.getName());
			try {
				LOGGER.info("Retrieving from disk " + repo.getName());
				repo.setGit(Git.open(folder));
			} catch (IOException e) {
				try {
					LOGGER.info("Git cloning " + repo.getName());
					repo.setGit(
							Git.cloneRepository().setURI(repo.getUrl() + GIT_EXTENSION).setDirectory(folder).call());
				} catch (GitAPIException e1) {
					// do nothing
				}
			}
			return repo;
		}).collect(Collectors.toList()).forEach(repo -> {
			try {

				var git = repo.getGit();

				var formatter = new DiffFormatter(new ByteArrayOutputStream());
				formatter.setRepository(git.getRepository());

				Iterable<RevCommit> log = git.log().call();
				List<CommitDTO> commits = new ArrayList<>();
				for (RevCommit entry : log) {
					DiffDTO diff = prepareDiffFromCommit(formatter, entry);

					PersonIdent author = entry.getAuthorIdent();
					Date commitedAt = Date.from(Instant.ofEpochSecond(entry.getCommitTime()));
					commits.add(new CommitDTO(
							new Commit(commitedAt, entry.getCommitterIdent().getName(), entry.getFullMessage().trim(), diff),
							author));
				}

				// Persists contributors and repositoryxcontributors
				var contributors = commits.stream()
						.filter(distinctByKey(c -> prepareNameContributor(c.getAuthor().getName())))
						.map(commit -> new Contributor(prepareNameContributor(commit)))
						.map(contributorDAO::persistIfNotExists).map(contributor -> {

							contributor.setId(Optional.ofNullable(contributor.getId())
									.orElseGet(() -> contributorDAO
											.findByUsername(prepareNameContributor(contributor.getUsername()))
											.orElseThrow().getId()));

							repositoryXContributorDAO.persistIfNotExists(new RepositoryXContributor(
									new RepositoryXContributorId(repo.getId(), contributor.getId()), false));

							return contributor;
						}).collect(Collectors.toList());

				// Persists the commits
				commits.stream().map(commitDTO -> {
					var contributor = contributors.stream()
							.filter(c -> c.getUsername().equals(prepareNameContributor(commitDTO))).findAny()
							.orElseThrow();
					Commit commit = commitDTO.getCommit();
					commit.setContributor(contributor);
					commit.setRepository(repo);

					return commit;
				}).forEach(commitDAO::persist);

			} catch (GitAPIException e) {
				// Do nothing
			}
		});
	}

	private static DiffDTO prepareDiffFromCommit(DiffFormatter formatter, RevCommit entry) {
		try {
			final RevTree a = entry.getParentCount() > 0 ? entry.getParent(0).getTree() : null;
			final RevTree b = entry.getTree();

			List<DiffEntry> diffs;
			diffs = formatter.scan(a, b);

			ToLongFunction<ChangeType> changeTypeCounter = changeType -> diffs.stream()
					.filter(d -> d.getChangeType().equals(changeType)).count();

			return new DiffDTO(diffs.size(), changeTypeCounter.applyAsLong(ChangeType.ADD),
					changeTypeCounter.applyAsLong(ChangeType.DELETE), changeTypeCounter.applyAsLong(ChangeType.MODIFY));
		} catch (IOException e) {
			return new DiffDTO(0, 0, 0, 0);
		}
	}

	private static String prepareNameContributor(CommitDTO commit) {
		var name = commit.getAuthor().getName();

		return name.equalsIgnoreCase("unknown") ? name.toLowerCase() : name;
	}

	private static String prepareNameContributor(String name) {

		return name.equalsIgnoreCase("unknown") ? name.toLowerCase() : name;
	}

	private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
		Set<Object> seen = ConcurrentHashMap.newKeySet();
		return t -> seen.add(keyExtractor.apply(t));
	}
}
