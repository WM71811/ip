public class Deadline extends Task {
    private String completeBy;

    public Deadline(String deadlineName, String completeBy) {
        super(deadlineName);
        this.completeBy = completeBy;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + completeBy + ")";
    }
}