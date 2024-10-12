package com.pg.crm.databinding;
import com.pg.crm.R;
import com.pg.crm.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentWeightmentBindingImpl extends FragmentWeightmentBinding  {

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
        sViewsWithIds.put(R.id.igpNoET, 7);
        sViewsWithIds.put(R.id.radio_grp, 8);
        sViewsWithIds.put(R.id.radio_btn, 9);
        sViewsWithIds.put(R.id.rbAuto, 10);
        sViewsWithIds.put(R.id.slNoET, 11);
        sViewsWithIds.put(R.id.customerNameACT, 12);
        sViewsWithIds.put(R.id.placeET, 13);
        sViewsWithIds.put(R.id.remarkET, 14);
        sViewsWithIds.put(R.id.productNameACT, 15);
        sViewsWithIds.put(R.id.qunatityET, 16);
        sViewsWithIds.put(R.id.containerET, 17);
        sViewsWithIds.put(R.id.tareWeight_ContainerET, 18);
        sViewsWithIds.put(R.id.grosswtET, 19);
        sViewsWithIds.put(R.id.totalTareWtET, 20);
        sViewsWithIds.put(R.id.netMaterialWeightET, 21);
        sViewsWithIds.put(R.id.newWeightBT, 22);
        sViewsWithIds.put(R.id.saveBT, 23);
        sViewsWithIds.put(R.id.continueWeightBT, 24);
        sViewsWithIds.put(R.id.modifyBT, 25);
        sViewsWithIds.put(R.id.clearBT, 26);
        sViewsWithIds.put(R.id.weighment_detail_LL, 27);
        sViewsWithIds.put(R.id.weightment_detail_rv, 28);
    }
    // views
    @NonNull
    private final android.widget.ScrollView mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentWeightmentBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 29, sIncludes, sViewsWithIds));
    }
    private FragmentWeightmentBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.Button) bindings[26]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[17]
            , (android.widget.Button) bindings[24]
            , (android.widget.AutoCompleteTextView) bindings[12]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[6]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[5]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[19]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[7]
            , (android.widget.LinearLayout) bindings[1]
            , (androidx.recyclerview.widget.RecyclerView) bindings[2]
            , (android.widget.AutoCompleteTextView) bindings[4]
            , (android.widget.LinearLayout) bindings[3]
            , (android.widget.Button) bindings[25]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[21]
            , (android.widget.Button) bindings[22]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[13]
            , (android.widget.AutoCompleteTextView) bindings[15]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[16]
            , (android.widget.RadioButton) bindings[9]
            , (android.widget.RadioGroup) bindings[8]
            , (android.widget.RadioButton) bindings[10]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[14]
            , (android.widget.Button) bindings[23]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[11]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[18]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[20]
            , (android.widget.LinearLayout) bindings[27]
            , (androidx.recyclerview.widget.RecyclerView) bindings[28]
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
        if (BR.weightmentViewModel == variableId) {
            setWeightmentViewModel((com.pg.crm.ui.weighment.WeightmentViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setWeightmentViewModel(@Nullable com.pg.crm.ui.weighment.WeightmentViewModel WeightmentViewModel) {
        this.mWeightmentViewModel = WeightmentViewModel;
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
        flag 0 (0x1L): weightmentViewModel
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}