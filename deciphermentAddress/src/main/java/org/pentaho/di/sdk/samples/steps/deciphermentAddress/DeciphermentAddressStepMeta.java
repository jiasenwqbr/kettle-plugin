package org.pentaho.di.sdk.samples.steps.deciphermentAddress;

import org.eclipse.swt.widgets.Shell;
import org.pentaho.di.core.CheckResult;
import org.pentaho.di.core.CheckResultInterface;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.exception.KettleStepException;
import org.pentaho.di.core.exception.KettleValueException;
import org.pentaho.di.core.exception.KettleXMLException;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.core.variables.VariableSpace;
import org.pentaho.di.core.xml.XMLHandler;
import org.pentaho.di.i18n.BaseMessages;
import org.pentaho.di.repository.ObjectId;
import org.pentaho.di.repository.Repository;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.*;
import org.pentaho.metastore.api.IMetaStore;
import org.w3c.dom.Node;

import java.util.List;

public class DeciphermentAddressStepMeta extends BaseStepMeta implements StepMetaInterface {
    private static Class<?> PKG =  DeciphermentAddressStepMeta.class;
    private String outputField = "地址";
    private String inputField = "地址";



    public String getOutputField() {
        return outputField;
    }

    public void setOutputField(String outputField) {
        this.outputField = outputField;
    }

    public String getInputField() {
        return inputField;
    }

    public void setInputField(String inputField) {
        this.inputField = inputField;
    }

    public String getRolePartten() {
        return rolePartten;
    }

    public void setRolePartten(String rolePartten) {
        this.rolePartten = rolePartten;
    }

    private String rolePartten = "";
    /**
     * This method is called every time a new step is created and should
     * allocate/set the step configuration to sensible defaults. The values set
     * here will be used by Spoon when a new step is created.
     */
    @Override
    public void setDefault() {

    }
    /**
     * Called by Spoon to get a new instance of the SWT dialog for the step. A
     * standard implementation passing the arguments to the constructor of the
     * step dialog is recommended.
     *
     * @param shell
     *            an SWT Shell
     * @param meta
     *            description of the step
     * @param transMeta
     *            description of the the transformation
     * @param name
     *            the name of the step
     * @return new instance of a dialog for this step
     */
    public StepDialogInterface getDialog(Shell shell, StepMetaInterface meta, TransMeta transMeta, String name) {
        return new DeciphermentAddressStepDialog(shell, meta, transMeta, name);
    }

    @Override
    public StepInterface getStep(StepMeta stepMeta, StepDataInterface stepDataInterface, int copyNr, TransMeta transMeta, Trans trans) {
        return new DeciphermentAddressStep(stepMeta, stepDataInterface, copyNr, transMeta, trans);
    }
    /**
     * Called by PDI to get a new instance of the step data class.
     */
    @Override
    public StepDataInterface getStepData() {
        return new DeciphermentAddressStepData();
    }
    /**
     * This method is used when a step is duplicated in Spoon. It needs to
     * return a deep copy of this step meta object. Be sure to create proper
     * deep copies if the step configuration is stored in modifiable objects.
     *
     * See org.pentaho.di.trans.steps.rowgenerator.RowGeneratorMeta.clone() for
     * an example on creating a deep copy.
     *
     * @return a deep copy of this
     */
    public Object clone() {
        Object retval = super.clone();
        return retval;
    }
    /**
     * 当一个步骤需要将其配置序列化为XML时，Spoon将调用此方法。预期的返回值是由一个或多个XML标记组成的XML片段。请使用org.pentaho
     * .di.core.xml。方便地生成XML This method is called by Spoon when a step needs to
     * serialize its configuration to XML. The expected return value is an XML
     * fragment consisting of one or more XML tags.
     *
     * Please use org.pentaho.di.core.xml.XMLHandler to conveniently generate
     * the XML.
     *
     * @return a string containing the XML serialization of this step
     */
    public String getXML() throws KettleValueException {
        StringBuilder xml = new StringBuilder();
        xml.append(XMLHandler.addTagValue("outputField", outputField));
        xml.append(XMLHandler.addTagValue("inputField", inputField));

        return xml.toString();
    }
    /**
     * 当步骤需要从XML加载其配置时，PDI将调用此方法。请使用org.pentaho.di.core.xml。，以便方便地从传入的XML节点。
     * This method is called by PDI when a step needs to load its configuration
     * from XML.
     *
     * Please use org.pentaho.di.core.xml.XMLHandler to conveniently read from
     * the XML node passed in.
     *
     * @param stepnode
     *            the XML node containing the configuration
     * @param databases
     *            the databases available in the transformation
     * @param metaStore
     *            the metaStore to optionally read from
     */
    public void loadXML(Node stepnode, List<DatabaseMeta> databases, IMetaStore metaStore) throws KettleXMLException {
        try {
            setOutputField(XMLHandler.getNodeValue(XMLHandler.getSubNode(stepnode, "outputField")));
            setInputField(XMLHandler.getNodeValue(XMLHandler.getSubNode(stepnode, "inputField")));


        } catch (Exception e) {
            throw new KettleXMLException("无法读取xml节点", e);
        }
    }
    /**
     * 当步骤需要将其配置序列化到存储库时，Spoon将调用此方法。repository实现提供了必要的方法来保存step属性。 This method
     * is called by Spoon when a step needs to serialize its configuration to a
     * repository. The repository implementation provides the necessary methods
     * to save the step attributes.
     *
     * @param rep
     *            the repository to save to
     * @param metaStore
     *            the metaStore to optionally write to
     * @param id_transformation
     *            the id to use for the transformation when saving
     * @param id_step
     *            the id to use for the step when saving
     */
    public void saveRep(Repository rep, IMetaStore metaStore, ObjectId id_transformation, ObjectId id_step)
            throws KettleException {
        try {
            rep.saveStepAttribute(id_transformation, id_step, "outputField", outputField);
            rep.saveStepAttribute(id_transformation, id_step, "inputField", inputField);


        } catch (Exception e) {
            throw new KettleException("Unable to save step into repository: " + id_step, e);
        }
    }
    /**
     * 当一个步骤需要从存储库中读取其配置时，PDI将调用此方法。存储库实现提供了必要的方法来读取步骤属性。 This method is called
     * by PDI when a step needs to read its configuration from a repository. The
     * repository implementation provides the necessary methods to read the step
     * attributes.
     *
     * @param rep
     *            the repository to read from
     * @param metaStore
     *            the metaStore to optionally read from
     * @param id_step
     *            the id of the step being read
     * @param databases
     *            the databases available in the transformation
     */
    public void readRep(Repository rep, IMetaStore metaStore, ObjectId id_step, List<DatabaseMeta> databases)
            throws KettleException {
        try {
            outputField = rep.getStepAttributeString(id_step, "outputField"); //$NON-NLS-1$
            inputField = rep.getStepAttributeString(id_step, "inputField"); //$NON-NLS-1$

        } catch (Exception e) {
            throw new KettleException("Unable to load step from repository", e);
        }
    }
    /**
     * This method is called to determine the changes the step is making to the
     * row-stream. To that end a RowMetaInterface object is passed in,
     * containing the row-stream structure as it is when entering the step. This
     * method must apply any changes the step makes to the row stream. Usually a
     * step adds fields to the row-stream.
     *
     * @param inputRowMeta
     *            the row structure coming in to the step
     * @param name
     *            the name of the step making the changes
     * @param info
     *            row structures of any info steps coming in
     * @param nextStep
     *            the description of a step this step is passing rows to
     * @param space
     *            the variable space for resolving variables
     * @param repository
     *            the repository instance optionally read from
     * @param metaStore
     *            the metaStore to optionally read from
     */
    public void getFields(RowMetaInterface inputRowMeta, String name, RowMetaInterface[] info, StepMeta nextStep,
                          VariableSpace space, Repository repository, IMetaStore metaStore) throws KettleStepException {

        /*
         * This implementation appends the outputField to the row-stream
         */

        // a value meta object contains the meta data for a field 创建包含该值的元数据对象
//		ValueMetaInterface encryptTypeValueMetaInterface = new ValueMetaString(encryptType);
        // setting trim type to "both"  去掉两边空格
//		encryptTypeValueMetaInterface.setTrimType(ValueMetaInterface.TRIM_TYPE_BOTH);
        // the name of the step that adds this field  添加步骤名称
//		encryptTypeValueMetaInterface.setOrigin(name);

        // modify the row structure and add the field this step generates
//		inputRowMeta.addValueMeta(encryptTypeValueMetaInterface);
    }

    /**
     * This method is called when the user selects the "Verify Transformation"
     * option in Spoon. A list of remarks is passed in that this method should
     * add to. Each remark is a comment, warning, error, or ok. The method
     * should perform as many checks as necessary to catch design-time errors.
     *
     * Typical checks include: - verify that all mandatory configuration is
     * given - verify that the step receives any input, unless it's a row
     * generating step - verify that the step does not receive any input if it
     * does not take them into account - verify that the step finds fields it
     * relies on in the row-stream
     *
     * @param remarks
     *            the list of remarks to append to
     * @param transMeta
     *            the description of the transformation
     * @param stepMeta
     *            the description of the step
     * @param prev
     *            the structure of the incoming row-stream
     * @param input
     *            names of steps sending input to the step
     * @param output
     *            names of steps this step is sending output to
     * @param info
     *            fields coming in from info steps
     * @param metaStore
     *            metaStore to optionally read from
     */
    public void check(List<CheckResultInterface> remarks, TransMeta transMeta, StepMeta stepMeta, RowMetaInterface prev,
                      String[] input, String[] output, RowMetaInterface info, VariableSpace space, Repository repository,
                      IMetaStore metaStore) {
        CheckResult cr;

        // See if there are input streams leading to this step!
        if (input != null && input.length > 0) {
            cr = new CheckResult(CheckResult.TYPE_RESULT_OK,
                    BaseMessages.getString(PKG, "Demo.CheckResult.ReceivingRows.OK"), stepMeta);
            remarks.add(cr);
        } else {
            cr = new CheckResult(CheckResult.TYPE_RESULT_ERROR,
                    BaseMessages.getString(PKG, "Demo.CheckResult.ReceivingRows.ERROR"), stepMeta);
            remarks.add(cr);
        }
    }



}
