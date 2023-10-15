package org.pentaho.di.sdk.samples.steps.deciphermentPhone;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.*;
import org.pentaho.di.core.Const;
import org.pentaho.di.i18n.BaseMessages;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.StepDialogInterface;
import org.pentaho.di.trans.step.StepMetaInterface;
import org.pentaho.di.ui.trans.step.BaseStepDialog;

public class DeciphermentPhoneStepDialog extends BaseStepDialog implements StepDialogInterface {

    private static Class<?> PKG = DeciphermentPhoneStepMeta.class;
    private DeciphermentPhoneStepMeta meta;


    // 字段
    private Label inputLabelName;
    private Text inputTextName;
    private FormData inputlValName, inputValName;



    public DeciphermentPhoneStepDialog(Shell parent, StepMetaInterface baseStepMeta, TransMeta transMeta, String stepname) {
        super(parent, baseStepMeta, transMeta, stepname);
        meta = (DeciphermentPhoneStepMeta) baseStepMeta;
    }

    @Override
    public String open() {


        Shell parent = getParent();
        Display display = parent.getDisplay();

        shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.RESIZE | SWT.MIN | SWT.MAX);
        props.setLook(shell);
        setShellImage(shell, meta);

        ModifyListener lsMod = new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                meta.setChanged();
            }
        };
        changed = meta.hasChanged();

        FormLayout formLayout = new FormLayout();
        formLayout.marginWidth = Const.FORM_MARGIN;
        formLayout.marginHeight = Const.FORM_MARGIN;

        shell.setLayout(formLayout);
        shell.setText("手机号码脱敏脱密");

        int middle = props.getMiddlePct();
        int margin = Const.MARGIN;

        // Stepname line
        wlStepname = new Label(shell, SWT.RIGHT);
        wlStepname.setText("步骤名称");
        props.setLook(wlStepname);
        fdlStepname = new FormData();
        fdlStepname.left = new FormAttachment(0, 0);
        fdlStepname.right = new FormAttachment(middle, -margin);
        fdlStepname.top = new FormAttachment(0, margin);
        wlStepname.setLayoutData(fdlStepname);

        wStepname = new Text(shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
        wStepname.setText(stepname);
        props.setLook(wStepname);
        wStepname.addModifyListener(lsMod);
        fdStepname = new FormData();
        fdStepname.left = new FormAttachment(middle, 0);
        fdStepname.top = new FormAttachment(0, margin);
        fdStepname.right = new FormAttachment(100, 0);
        wStepname.setLayoutData(fdStepname);

        // output dummy value
//        wlValName = new Label(shell, SWT.RIGHT);
//        wlValName.setText("输出字段");
//        props.setLook(wlValName);
//        fdlValName = new FormData();
//        fdlValName.left = new FormAttachment(0, 0);
//        fdlValName.right = new FormAttachment(middle, -margin);
//        fdlValName.top = new FormAttachment(wStepname, margin);
//        wlValName.setLayoutData(fdlValName);
//
//        wValName = new Text(shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
//        props.setLook(wValName);
//        wValName.addModifyListener(lsMod);
//        fdValName = new FormData();
//        fdValName.left = new FormAttachment(middle, 0);
//        fdValName.right = new FormAttachment(100, 0);
//        fdValName.top = new FormAttachment(wStepname, margin);
//        wValName.setLayoutData(fdValName);

        // 字段
        inputLabelName = new Label(shell, SWT.RIGHT);
        inputLabelName.setText("脱敏脱密字段");
        props.setLook(inputLabelName);
        inputlValName = new FormData();
        inputlValName.left = new FormAttachment(0, 0);
        inputlValName.right = new FormAttachment(middle, -margin);
        inputlValName.top = new FormAttachment(wStepname, margin);
        inputLabelName.setLayoutData(inputlValName);

        inputTextName = new Text(shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
        props.setLook(inputTextName);
        inputTextName.addModifyListener(lsMod);
        inputValName = new FormData();
        inputValName.left = new FormAttachment(middle, 0);
        inputValName.right = new FormAttachment(100, 0);
        inputValName.top = new FormAttachment(wStepname, margin);
        inputTextName.setLayoutData(inputValName);




        // OK and cancel buttons
        wOK = new Button(shell, SWT.PUSH);
        wOK.setText(BaseMessages.getString(PKG, "System.Button.OK"));
        wCancel = new Button(shell, SWT.PUSH);
        wCancel.setText(BaseMessages.getString(PKG, "System.Button.Cancel"));

        BaseStepDialog.positionBottomButtons(shell, new Button[] { wOK, wCancel }, margin, inputTextName);

        // Add listeners
        lsCancel = new Listener() {
            public void handleEvent(Event e) {
                cancel();
            }
        };
        lsOK = new Listener() {
            public void handleEvent(Event e) {
                ok();
            }
        };

        wCancel.addListener(SWT.Selection, lsCancel);
        wOK.addListener(SWT.Selection, lsOK);

        lsDef = new SelectionAdapter() {
            public void widgetDefaultSelected(SelectionEvent e) {
                ok();
            }
        };

        wStepname.addSelectionListener(lsDef);
        //wValName.addSelectionListener(lsDef);

        // Detect X or ALT-F4 or something that kills this window...
        shell.addShellListener(new ShellAdapter() {
            public void shellClosed(ShellEvent e) {
                cancel();
            }
        });

        // Set the shell size, based upon previous time...
        setSize();

        getData();
        meta.setChanged(changed);

        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch())
                display.sleep();
        }
        return stepname;
    }


    // Read data and place it in the dialog
    public void getData() {
        wStepname.selectAll();
       // wValName.setText(meta.getOutputField());
        if (meta.getInputField()!=null){
            inputTextName.setText(meta.getInputField());
        }


    }

    private void cancel() {
        stepname = null;
        meta.setChanged(changed);
        dispose();
    }

    // let the plugin know about the entered data
    private void ok() {
        stepname = wStepname.getText(); // return value

        meta.setInputField(inputTextName.getText());

        dispose();
    }

    /**
     * This helper method puts the step configuration stored in the meta object
     * and puts it into the dialog controls.
     */
    private void populateDialog() {
        wStepname.selectAll();

        inputTextName.setText(meta.getInputField());

    }

}
