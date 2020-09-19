package edu.northeastern.cs5500.recipe.controller;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

import edu.northeastern.cs5500.recipe.model.Step;
import java.net.UnknownHostException;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class StepControllerTest {
    @Test
    void testRegisterCreatesSteps() throws UnknownHostException {
        StepController stepController = new StepController();
        stepController.register();
        assertThat(stepController.getSteps("YiwenZhang_slava_chiken")).isNotEmpty();
    }

    @Test
    void testRegisterCreatesValidSteps() throws UnknownHostException {
        // TODO: Why is this test failing?
        StepController stepController = new StepController();
        stepController.register();

        for (Step step : stepController.getSteps("YiwenZhang_slava_chiken")) {
            assertWithMessage(step.getStepId()).that(step.isValid()).isTrue();
        }
    }

    @Test
    void testCanAddStep() throws Exception {
        StepController stepController = new StepController();
        Step step = new Step("YiwenZhang_slava_chiken", 5, "boil the chiken", "");
        stepController.addStep(step);
        Assert.assertEquals(stepController.getSteps("YiwenZhang_slava_chiken").size(), 3);
        stepController.deleteStep("YiwenZhang_slava_chiken5");
    }

    @Test
    void testCanReplaceStep() throws Exception {
        StepController stepController = new StepController();
        Step step = new Step("YiwenZhang_slava_chiken", 5, "boil the chiken", "");
        stepController.addStep(step);
        Step updateStep = new Step("YiwenZhang_slava_chiken", 5, "fry the chiken", "");
        stepController.updateStep(updateStep);
        Assert.assertEquals(
                stepController.getStep("YiwenZhang_slava_chiken5").getDescription(),
                "fry the chiken");
        stepController.deleteStep("YiwenZhang_slava_chiken5");
    }
}
