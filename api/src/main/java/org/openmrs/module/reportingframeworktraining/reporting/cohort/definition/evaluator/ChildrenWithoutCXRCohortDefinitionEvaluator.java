package org.openmrs.module.reportingframeworktraining.reporting.cohort.definition.evaluator;

import org.openmrs.Cohort;
import org.openmrs.Concept;
import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.cohort.EvaluatedCohort;
import org.openmrs.module.reporting.cohort.definition.AgeCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.CodedObsCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.CompositionCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.service.CohortDefinitionService;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: oliver
 * Date: 6/4/13
 * Time: 10:07 AM
 * To change this template use File | Settings | File Templates.
 */
public class ChildrenWithoutCXRCohortDefinitionEvaluator {

    public EvaluatedCohort evaluate(CohortDefinition cohortDefinition, EvaluationContext context) throws EvaluationException {

        AgeCohortDefinition ageCohortDefinition = new AgeCohortDefinition();
        ageCohortDefinition.setMaxAge(17);

        CodedObsCohortDefinition codedObsCohortDefinition = new CodedObsCohortDefinition();
        codedObsCohortDefinition.setQuestion(new Concept(7859));
        codedObsCohortDefinition.setValueList(Arrays.asList(new Concept(1065)));

        CompositionCohortDefinition mergedCohortDef = new CompositionCohortDefinition();
        mergedCohortDef.addSearch("ageCohortDef",ageCohortDefinition,null);
        mergedCohortDef.addSearch("obsCohortDef",codedObsCohortDefinition, null);
        mergedCohortDef.setCompositionString("AND");

        Cohort results = Context.getService(CohortDefinitionService.class).evaluate(mergedCohortDef, context);


        return new EvaluatedCohort(results, mergedCohortDef, context);
    }
}
