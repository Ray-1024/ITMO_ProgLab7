package ray1024.projects.collectioncontroller.commands;

import ray1024.projects.collectioncontroller.tools.Phrases;

/**
 * Выводит те элементы коллекции, которые имеют меньшее колличество студентов в группе чем задано в параметре
 */
public class FilterLessThanStudentsCountCommand extends BaseCommand {
    int studentsCount = 0;
    public static final FilterLessThanStudentsCountCommand command = new FilterLessThanStudentsCountCommand();

    private FilterLessThanStudentsCountCommand() {
        setName("filter_less_than_students_count").setDescription(Phrases.getPhrase("FilterLessThanStudentsCountCommandDescription"));
        CommandRegister.registerCommand(this);
    }

    @Override
    public void execute() {
        final int[] ind = new int[1];
        getParentShell().getParentTerminal().getCollectionController().getManagedCollection().stream()
                .filter((elem) -> elem.getStudentsCount() < studentsCount).forEach((elem) -> {
                    getParentShell().getParentTerminal().getWriter().println(String.format("\t%d. %s", ++ind[0], elem));
                });
    }

    @Override
    public BaseCommand setArgs(String[] args) throws RuntimeException {
        if (args != null && args.length == 2) {
            try {
                studentsCount = Integer.parseInt(args[1]);
                return this;
            } catch (NumberFormatException ignored) {
            }
        }
        throw new RuntimeException(Phrases.getPhrase("WrongCommandArgs"));
    }

    @Override
    public void inputLine(String line) throws IllegalStateException {

    }

    @Override
    public String getStepDescription() {
        return "";
    }
}
