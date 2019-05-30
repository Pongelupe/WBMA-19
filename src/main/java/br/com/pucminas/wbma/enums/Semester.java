package br.com.pucminas.wbma.enums;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

public enum Semester {

	S1_16("01/02/2019", 0), S2_16("01/08/2019", 1), S1_17("01/02/2017", 2), S2_17("01/08/2017", 3),
	S1_18("01/02/2018", 4), S2_18("01/08/2018", 5), S1_19("01/02/2019", 6);

	final Date dateSemester;
	final int order;
	private final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

	private Semester(String semester, int order) {
		this.order = order;
		try {
			this.dateSemester = formatter.parse(semester);
		} catch (ParseException e) {
			throw new IllegalArgumentException(e);
		}
	}

	public Date getDateSemester() {
		return dateSemester;
	}

	public static Optional<Semester> getSemesterByOrder(int order) {
		return Arrays.asList(values()).stream().filter(s -> s.order == order).findAny();
	}

}
