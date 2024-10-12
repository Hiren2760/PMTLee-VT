package com.pg.crm.databinding;
import com.pg.crm.R;
import com.pg.crm.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentMaterialInwardBindingImpl extends FragmentMaterialInwardBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.ly_location, 1);
        sViewsWithIds.put(R.id.locationACT, 2);
        sViewsWithIds.put(R.id.enteredByET, 3);
        sViewsWithIds.put(R.id.datetimeET, 4);
        sViewsWithIds.put(R.id.igpNoET, 5);
        sViewsWithIds.put(R.id.customerNameET, 6);
        sViewsWithIds.put(R.id.placeET, 7);
        sViewsWithIds.put(R.id.dcDateET, 8);
        sViewsWithIds.put(R.id.dcNoET, 9);
        sViewsWithIds.put(R.id.vehicleNoET, 10);
        sViewsWithIds.put(R.id.driverNameET, 11);
        sViewsWithIds.put(R.id.materialTypeAct, 12);
        sViewsWithIds.put(R.id.materialDatailsET, 13);
        sViewsWithIds.put(R.id.remarkET, 14);
        sViewsWithIds.put(R.id.newBT, 15);
        sViewsWithIds.put(R.id.saveBT, 16);
        sViewsWithIds.put(R.id.findBT, 17);
        sViewsWithIds.put(R.id.modifyBT, 18);
        sViewsWithIds.put(R.id.clearBT, 19);
    }
    // views
    @NonNull
    private final android.widget.ScrollView mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentMaterialInwardBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 20, sIncludes, sViewsWithIds));
    }
    private FragmentMaterialInwardBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.Button) bindings[19]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[6]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[4]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[8]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[9]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[11]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[3]
            , (android.widget.Button) bindings[17]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[5]
            , (android.widget.AutoCompleteTextView) bindings[2]
            , (android.widget.LinearLayout) bindings[1]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[13]
            , (android.widget.AutoCompleteTextView) bindings[12]
            , (android.widget.Button) bindings[18]
            , (android.widget.Button) bindings[15]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[7]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[14]
            , (android.widget.Button) bindings[16]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[10]
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
        if (BR.materialInwardViewModel == variableId) {
            setMaterialInwardViewModel((com.pg.crm.ui.materialInward.MaterialInwardViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setMaterialInwardViewModel(@Nullable com.pg.crm.ui.materialInward.MaterialInwardViewModel MaterialInwardViewModel) {
        this.mMaterialInwardViewModel = MaterialInwardViewModel;
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
        flag 0 (0x1L): materialInwardViewModel
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}