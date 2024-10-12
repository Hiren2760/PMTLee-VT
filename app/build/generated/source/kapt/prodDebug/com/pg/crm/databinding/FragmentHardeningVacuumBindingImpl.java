package com.pg.crm.databinding;
import com.pg.crm.R;
import com.pg.crm.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentHardeningVacuumBindingImpl extends FragmentHardeningVacuumBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.inwardPendingDetail_LLs, 1);
        sViewsWithIds.put(R.id.pendingDetailCustomerRV, 2);
        sViewsWithIds.put(R.id.ly_location, 3);
        sViewsWithIds.put(R.id.locationACT, 4);
        sViewsWithIds.put(R.id.enteredByET, 5);
        sViewsWithIds.put(R.id.ly_status, 6);
        sViewsWithIds.put(R.id.statusACT, 7);
        sViewsWithIds.put(R.id.ly_processing_shift, 8);
        sViewsWithIds.put(R.id.processingShiftACT, 9);
        sViewsWithIds.put(R.id.helpersET, 10);
        sViewsWithIds.put(R.id.ly_change_over_shift, 11);
        sViewsWithIds.put(R.id.changeOverShiftACT, 12);
        sViewsWithIds.put(R.id.shiftEt, 13);
        sViewsWithIds.put(R.id.ly_cycle_running_in, 14);
        sViewsWithIds.put(R.id.cycleRunningIn, 15);
        sViewsWithIds.put(R.id.fullLoadHeatingEt, 16);
        sViewsWithIds.put(R.id.fullLoadCoolingEt, 17);
        sViewsWithIds.put(R.id.helpers1ET, 18);
        sViewsWithIds.put(R.id.datetimeET, 19);
        sViewsWithIds.put(R.id.machineNoEt, 20);
        sViewsWithIds.put(R.id.startTimeET, 21);
        sViewsWithIds.put(R.id.programNoEt, 22);
        sViewsWithIds.put(R.id.tempET, 23);
        sViewsWithIds.put(R.id.loadingTimeInET, 24);
        sViewsWithIds.put(R.id.pistonBarET, 25);
        sViewsWithIds.put(R.id.pistonPumpTime, 26);
        sViewsWithIds.put(R.id.rootsPumpBarET, 27);
        sViewsWithIds.put(R.id.rootsPumpTimeET, 28);
        sViewsWithIds.put(R.id.holdingPumpBarET, 29);
        sViewsWithIds.put(R.id.holdingPumpTimeET, 30);
        sViewsWithIds.put(R.id.diffusionPumpBarET, 31);
        sViewsWithIds.put(R.id.diffusionPumpTimeET, 32);
        sViewsWithIds.put(R.id.thyristorBarET, 33);
        sViewsWithIds.put(R.id.thyristorOnTimeET, 34);
        sViewsWithIds.put(R.id.ln2InitialET, 35);
        sViewsWithIds.put(R.id.ln2FinalET, 36);
        sViewsWithIds.put(R.id.ln2InitialMB1ET, 37);
        sViewsWithIds.put(R.id.ln2FinalMB1ET, 38);
        sViewsWithIds.put(R.id.ln2InitialMB2ET, 39);
        sViewsWithIds.put(R.id.ln2FinalMB2ET, 40);
        sViewsWithIds.put(R.id.endTimeET, 41);
        sViewsWithIds.put(R.id.durationET, 42);
        sViewsWithIds.put(R.id.startProcessBT, 43);
        sViewsWithIds.put(R.id.endProcessBT, 44);
        sViewsWithIds.put(R.id.saveBT, 45);
        sViewsWithIds.put(R.id.findBT, 46);
        sViewsWithIds.put(R.id.modifyBT, 47);
        sViewsWithIds.put(R.id.clearBT, 48);
    }
    // views
    @NonNull
    private final androidx.core.widget.NestedScrollView mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentHardeningVacuumBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 49, sIncludes, sViewsWithIds));
    }
    private FragmentHardeningVacuumBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.AutoCompleteTextView) bindings[12]
            , (android.widget.Button) bindings[48]
            , (android.widget.AutoCompleteTextView) bindings[15]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[19]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[31]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[32]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[42]
            , (android.widget.Button) bindings[44]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[41]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[5]
            , (android.widget.Button) bindings[46]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[17]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[16]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[18]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[10]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[29]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[30]
            , (android.widget.LinearLayout) bindings[1]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[36]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[38]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[40]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[35]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[37]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[39]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[24]
            , (android.widget.AutoCompleteTextView) bindings[4]
            , (android.widget.LinearLayout) bindings[11]
            , (android.widget.LinearLayout) bindings[14]
            , (android.widget.LinearLayout) bindings[3]
            , (android.widget.LinearLayout) bindings[8]
            , (android.widget.LinearLayout) bindings[6]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[20]
            , (android.widget.Button) bindings[47]
            , (androidx.recyclerview.widget.RecyclerView) bindings[2]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[25]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[26]
            , (android.widget.AutoCompleteTextView) bindings[9]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[22]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[27]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[28]
            , (android.widget.Button) bindings[45]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[13]
            , (android.widget.Button) bindings[43]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[21]
            , (android.widget.AutoCompleteTextView) bindings[7]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[23]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[33]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[34]
            );
        this.mboundView0 = (androidx.core.widget.NestedScrollView) bindings[0];
        this.mboundView0.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x2L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.hardeningVacuumViewModel == variableId) {
            setHardeningVacuumViewModel((com.pg.crm.ui.hardeningVacuum.HardeningVacuumViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setHardeningVacuumViewModel(@Nullable com.pg.crm.ui.hardeningVacuum.HardeningVacuumViewModel HardeningVacuumViewModel) {
        this.mHardeningVacuumViewModel = HardeningVacuumViewModel;
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        // batch finished
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): hardeningVacuumViewModel
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}