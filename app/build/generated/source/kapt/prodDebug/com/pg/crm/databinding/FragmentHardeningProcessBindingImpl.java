package com.pg.crm.databinding;
import com.pg.crm.R;
import com.pg.crm.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentHardeningProcessBindingImpl extends FragmentHardeningProcessBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.inwardPendingDetail_LL, 1);
        sViewsWithIds.put(R.id.htPendingBatchDetails_detail_rv, 2);
        sViewsWithIds.put(R.id.ly_location, 3);
        sViewsWithIds.put(R.id.locationACT, 4);
        sViewsWithIds.put(R.id.enteredByET, 5);
        sViewsWithIds.put(R.id.ly_status, 6);
        sViewsWithIds.put(R.id.statusAct, 7);
        sViewsWithIds.put(R.id.datetimeET, 8);
        sViewsWithIds.put(R.id.machineNoEt, 9);
        sViewsWithIds.put(R.id.programeNoET, 10);
        sViewsWithIds.put(R.id.startingTimeET, 11);
        sViewsWithIds.put(R.id.oilTempET, 12);
        sViewsWithIds.put(R.id.timeInET, 13);
        sViewsWithIds.put(R.id.austTempET, 14);
        sViewsWithIds.put(R.id.tempAchdET, 15);
        sViewsWithIds.put(R.id.sampleECD1ET, 16);
        sViewsWithIds.put(R.id.sampleECD2ET, 17);
        sViewsWithIds.put(R.id.sampleECD3ET, 18);
        sViewsWithIds.put(R.id.coolingStarET, 19);
        sViewsWithIds.put(R.id.highTempET, 20);
        sViewsWithIds.put(R.id.oilTempBeforeET, 21);
        sViewsWithIds.put(R.id.oilTempAfterET, 22);
        sViewsWithIds.put(R.id.asQuenchedECDET, 23);
        sViewsWithIds.put(R.id.asQuenchedCHET, 24);
        sViewsWithIds.put(R.id.asQuenchedSHET, 25);
        sViewsWithIds.put(R.id.endTimeET, 26);
        sViewsWithIds.put(R.id.durationET, 27);
        sViewsWithIds.put(R.id.startProcessBT, 28);
        sViewsWithIds.put(R.id.endProcessBT, 29);
        sViewsWithIds.put(R.id.saveBT, 30);
        sViewsWithIds.put(R.id.findBT, 31);
        sViewsWithIds.put(R.id.modifyBT, 32);
        sViewsWithIds.put(R.id.clearBT, 33);
    }
    // views
    @NonNull
    private final android.widget.ScrollView mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentHardeningProcessBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 34, sIncludes, sViewsWithIds));
    }
    private FragmentHardeningProcessBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (androidx.appcompat.widget.AppCompatEditText) bindings[24]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[23]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[25]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[14]
            , (android.widget.Button) bindings[33]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[19]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[8]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[27]
            , (android.widget.Button) bindings[29]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[26]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[5]
            , (android.widget.Button) bindings[31]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[20]
            , (androidx.recyclerview.widget.RecyclerView) bindings[2]
            , (android.widget.LinearLayout) bindings[1]
            , (android.widget.AutoCompleteTextView) bindings[4]
            , (android.widget.LinearLayout) bindings[3]
            , (android.widget.LinearLayout) bindings[6]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[9]
            , (android.widget.Button) bindings[32]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[22]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[21]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[12]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[10]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[16]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[17]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[18]
            , (android.widget.Button) bindings[30]
            , (android.widget.Button) bindings[28]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[11]
            , (android.widget.AutoCompleteTextView) bindings[7]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[15]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[13]
            );
        this.mboundView0 = (android.widget.ScrollView) bindings[0];
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
        if (BR.hardeningProcessViewModel == variableId) {
            setHardeningProcessViewModel((com.pg.crm.ui.hardening.HardeningProcessViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setHardeningProcessViewModel(@Nullable com.pg.crm.ui.hardening.HardeningProcessViewModel HardeningProcessViewModel) {
        this.mHardeningProcessViewModel = HardeningProcessViewModel;
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
        flag 0 (0x1L): hardeningProcessViewModel
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}