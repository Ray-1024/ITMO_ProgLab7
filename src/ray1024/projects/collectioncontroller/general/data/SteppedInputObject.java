package ray1024.projects.collectioncontroller.general.data;

import ray1024.projects.collectioncontroller.general.interfaces.ISteppedInput;

public abstract class SteppedInputObject implements ISteppedInput {
    protected int currentStep = 0;
    protected int stepsCount = 0;

    @Override
    public void reset() {
        currentStep = 0;
    }

    public int getStepsCount() {
        return stepsCount;
    }

    @Override
    public boolean isObjectReady() {
        return currentStep >= stepsCount;
    }
}
