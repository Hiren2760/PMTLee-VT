package com.pg.crm.databinding;
import com.pg.crm.R;
import com.pg.crm.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentBatchScheduleBindingImpl extends FragmentBatchScheduleBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.inwardPendingDetail_LL, 1);
        sViewsWithIds.put(R.id.inwardPending_detail_rv, 2);
        sViewsWithIds.put(R.id.ly_location, 3);
        sViewsWithIds.put(R.id.locationACT, 4);
        sViewsWithIds.put(R.id.enteredByET, 5);
        sViewsWithIds.put(R.id.datetimeET, 6);
        sViewsWithIds.put(R.id.proposedSchDateET, 7);
        sViewsWithIds.put(R.id.proposedSchTimeET, 8);
        sViewsWithIds.put(R.id.batchSchNoET, 9);
        sViewsWithIds.put(R.id.shift, 10);
        sViewsWithIds.put(R.id.processGroupET, 11);
        sViewsWithIds.put(R.id.processNameET, 12);
        sViewsWithIds.put(R.id.tank_MachineET, 13);
        sViewsWithIds.put(R.id.capacityET, 14);
        sViewsWithIds.put(R.id.programeNoET, 15);
        sViewsWithIds.put(R.id.programDurationET, 16);
        sViewsWithIds.put(R.id.totalQuantityET, 17);
        sViewsWithIds.put(R.id.remarkET, 18);
        sViewsWithIds.put(R.id.newBatchBT, 19);
        sViewsWithIds.put(R.id.saveBT, 20);
        sViewsWithIds.put(R.id.findBT, 21);
        sViewsWithIds.put(R.id.modifyBT, 22);
        sViewsWithIds.put(R.id.clearBT, 23);
    }
    // views
    @NonNull
    private final android.widget.ScrollView mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentBatchScheduleBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 24, sIncludes, sViewsWithIds));
    }
    private FragmentBatchScheduleBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (androidx.appcompat.widget.AppCompatEditText) bindings[9]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[14]
            , (android.widget.Button) bindings[23]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[6]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[5]
            , (android.widget.Button) bindings[21]
            , (android.widget.LinearLayout) bindings[1]
            , (androidx.recyclerview.widget.RecyclerView) bindings[2]
            , (android.widget.AutoCompleteTextView) bindings[4]
            , (android.widget.LinearLayout) bindings[3]
            , (android.widget.Button) bindings[22]
            , (android.widget.Button) bindings[19]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[11]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[12]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[16]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[15]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[7]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[8]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[18]
            , (android.widget.Button) bindings[20]
            , (android.widget.AutoCompleteTextView) bindings[10]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[13]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[17]
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
        if (BR.batchSchViewModel == variableId) {
            setBatchSchViewModel((com.pg.crm.ui.batchSchedule.BatchSchViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setBatchSchViewModel(@Nullable com.pg.crm.ui.batchSchedule.BatchSchViewModel BatchSchViewModel) {
        this.mBatchSchViewModel = BatchSchViewModel;
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
        flag 0 (0x1L): batchSchViewModel
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}