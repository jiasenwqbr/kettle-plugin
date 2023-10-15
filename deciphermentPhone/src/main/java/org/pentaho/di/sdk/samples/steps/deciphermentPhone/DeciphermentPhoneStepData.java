package org.pentaho.di.sdk.samples.steps.deciphermentPhone;

import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.trans.step.BaseStepData;
import org.pentaho.di.trans.step.StepDataInterface;

public class DeciphermentPhoneStepData extends BaseStepData implements StepDataInterface {
    RowMetaInterface outputRowMeta;
    int inputFieldIndex = -1;

    public DeciphermentPhoneStepData() {
        super();
    }
}
