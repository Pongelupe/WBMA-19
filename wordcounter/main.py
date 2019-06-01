import sys
import mysql.connector
from utils import text_utility, image_utility


def main():
    mydb = mysql.connector.connect(
        host="mysql465.umbler.com",
        user="",
        password="",
        port=41890
    )

    coursename = sys.argv[1]
    amountofwords = int(sys.argv[2] if len(sys.argv) == 3 else 0)

    mycursor = mydb.cursor()

    mycursor.execute("SELECT CT.message "
                     "FROM wbma.course C "
                     "JOIN wbma.repository R ON R.course_id = C.id "
                     "JOIN wbma.commit CT ON CT.repository_id = R.ID "
                     "WHERE C.name = '{0}'".format(coursename))

    messages = [message[0] for message in mycursor.fetchall()]

    words = text_utility.get_filtered_words(messages)
    word_frequency = text_utility.get_word_frequency(words)

    sorted_by_frequency = sorted(((value, key) for (key, value) in word_frequency.items()), reverse=True)
    sorted_by_frequency = (sorted_by_frequency[:amountofwords] if amountofwords > 0 else sorted_by_frequency)

    word_frequency_dict = dict((word[1], word[0]) for word in sorted_by_frequency)

    wordcloud = image_utility.get_wordcloud(word_frequency_dict).to_image()
    wordcloud.show()


main()
