package ray1024.projects.collectioncontroller.general.commands;

import ray1024.projects.collectioncontroller.general.communication.Response;
import ray1024.projects.collectioncontroller.general.communication.ResponseType;
import ray1024.projects.collectioncontroller.general.data.StudyGroup;
import ray1024.projects.collectioncontroller.general.tools.Phrases;

/**
 * Команда добавляет в коллекцию элемент
 */
public class AddCommand extends BaseCommand {
    private StudyGroup studyGroup = new StudyGroup();
    public static final AddCommand command = new AddCommand();

    private AddCommand() {
        setName("add").setDescription(Phrases.getPhrase("AddCommandDescription"));
        stepsCount = studyGroup.getStepsCount();
        CommandRegister.registerCommand(this);
    }


    @Override
    public void run() {
        try {
            studyGroup.setId(StudyGroup.getNextID());
            StudyGroup.setNextID(StudyGroup.getNextID() + 1);
            getTerminal().getCollectionController().add(studyGroup.setOwnen(getUser()));

        } catch (Exception e) {
            e.printStackTrace();
            getTerminal().getServer().getResponseSender().sendResponse(new Response().setAnswer(Phrases.getPhrase("Can'tExecuteCommand")).setResponseType(ResponseType.ANSWER), getUser().getConnector());
        }
    }

    @Override
    public void inputLine(String line) throws IllegalStateException {
        studyGroup.inputLine(line);
    }

    @Override
    public boolean isObjectReady() {
        return studyGroup.isObjectReady();
    }

    @Override
    public String getStepDescription() {
        return studyGroup.getStepDescription();
    }

    @Override
    public BaseCommand setArgs(String[] args) {
        try {
            studyGroup = new StudyGroup();
            if (args == null || args.length != 1)
                getTerminal().getWriter().println(Phrases.getPhrase("WrongCommandArgs"));
        } catch (Throwable ignored) {
        }
        return this;
    }

    @Override
    public void reset() {
        currentStep = 0;
        studyGroup = new StudyGroup();
    }
}
