package ray1024.projects.collectioncontroller.general.commands;

import ray1024.projects.collectioncontroller.general.communication.Response;
import ray1024.projects.collectioncontroller.general.communication.ResponseType;
import ray1024.projects.collectioncontroller.general.tools.Phrases;

/**
 * Показывает все элементы коллекции
 */
public class ShowCommand extends BaseCommand {
    public static final ShowCommand command = new ShowCommand();

    private ShowCommand() {
        setName("show").setDescription(Phrases.getPhrase("ShowCommandDescription"));
        CommandRegister.registerCommand(this);
    }

    @Override
    public void run() {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            int[] ind = new int[1];
            getTerminal().getCollectionController().getManagedCollection().stream().forEach((elem -> {
                stringBuilder.append((++ind[0]) + elem.toString());
                stringBuilder.append("\n");
            }));
            getTerminal().getServer().getResponseSender().sendResponse(new Response().setResponseType(ResponseType.ANSWER).setAnswer(stringBuilder.toString()), getUser().getConnector());
        } catch (Throwable ignored) {
        }
    }

    @Override
    public void inputLine(String line) throws IllegalStateException {

    }

    @Override
    public String getStepDescription() {
        return "";
    }

    @Override
    public BaseCommand setArgs(String[] args) throws RuntimeException {
        if (args == null || args.length != 1) throw new RuntimeException(Phrases.getPhrase("WrongCommandArgs"));
        return this;
    }
}
