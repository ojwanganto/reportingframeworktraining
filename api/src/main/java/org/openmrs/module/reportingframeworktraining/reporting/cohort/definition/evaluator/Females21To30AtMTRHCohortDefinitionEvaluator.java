package org.openmrs.module.reportingframeworktraining.reporting.cohort.definition.evaluator;

import org.openmrs.Cohort;
import org.openmrs.Concept;
import org.openmrs.Location;
import org.openmrs.api.PatientSetService;
import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.cohort.EvaluatedCohort;
import org.openmrs.module.reporting.cohort.definition.*;
import org.openmrs.module.reporting.cohort.definition.service.CohortDefinitionService;
import org.openmrs.module.reporting.common.RangeComparator;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: oliver
 * Date: 6/4/13
 * Time: 11:56 AM
 * To change this template use File | Settings | File Templates.
 */
public class Females21To30AtMTRHCohortDefinitionEvaluator {

    public EvaluatedCohort evaluate(CohortDefinition cohortDefinition, EvaluationContext context) throws EvaluationException {

        AgeCohortDefinition ageCohortDefinition = new AgeCohortDefinition();
        ageCohortDefinition.setMaxAge(30);
        ageCohortDefinition.setMinAge(21);

        NumericObsCohortDefinition cd4FacsCohortDef = new NumericObsCohortDefinition();
        cd4FacsCohortDef.setQuestion(new Concept(5497));
        cd4FacsCohortDef.setOperator1(RangeComparator.LESS_THAN);
        cd4FacsCohortDef.setValue1(300d);

        NumericObsCohortDefinition mtrhCohort = new NumericObsCohortDefinition();
        mtrhCohort.setTimeModifier(PatientSetService.TimeModifier.ANY);
        mtrhCohort.setQuestion(new Concept(6708));
        mtrhCohort.setLocationList(Arrays.asList(new Location(10),new Location(11),new Location(13),new Location(14)));


        CompositionCohortDefinition ageAndCD4Def = new CompositionCohortDefinition();
        ageAndCD4Def.addSearch("ageCohortDef",ageCohortDefinition,null);
        ageAndCD4Def.addSearch("cd4FacsCohortDef",cd4FacsCohortDef, null);
        ageAndCD4Def.setCompositionString("AND");

        CompositionCohortDefinition finalCompositionCohortDef = new CompositionCohortDefinition();
        finalCompositionCohortDef.addSearch("ageAndCD4",ageAndCD4Def,null);
        finalCompositionCohortDef.addSearch("mtrhCohortDef",mtrhCohort, null);
        finalCompositionCohortDef.setCompositionString("AND");


        Cohort results = Context.getService(CohortDefinitionService.class).evaluate(finalCompositionCohortDef, context);


        return new EvaluatedCohort(results, finalCompositionCohortDef, context);
    }

}
