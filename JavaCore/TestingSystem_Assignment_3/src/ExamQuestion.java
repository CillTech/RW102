public class ExamQuestion {
    private Exam exam;
    private Question question;

    public ExamQuestion(Exam exam, Question question) {
        this.exam = exam;
        this.question = question;
    }

    public Exam getExam() { return exam; }
    public void setExam(Exam exam) { this.exam = exam; }
    public Question getQuestion() { return question; }
    public void setQuestion(Question question) { this.question = question; }

    @Override
    public String toString() {
        return "ExamQuestion [Exam=" + (exam != null ? exam.getTitle() : null) +
                ", Question=" + (question != null ? question.getContent() : null) + "]";
    }
}