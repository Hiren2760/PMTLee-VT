package com.pg.crm.databinding;
import com.pg.crm.R;
import com.pg.crm.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentCustomerWiseItemRateDetailsBindingImpl extends FragmentCustomerWiseItemRateDetailsBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.ly_location, 1);
        sViewsWithIds.put(R.id.locationACT, 2);
        sViewsWithIds.put(R.id.EnteredByET, 3);
        sViewsWithIds.put(R.id.ly_status, 4);
        sViewsWithIds.put(R.id.DateTimeACT, 5);
        sViewsWithIds.put(R.id.ly_processing_shift, 6);
        sViewsWithIds.put(R.id.CustomerNameACT, 7);
        sViewsWithIds.put(R.id.ItemNameACT, 8);
        sViewsWithIds.put(R.id.ly_change_over_shift, 9);
        sViewsWithIds.put(R.id.ItemCodeACT, 10);
        sViewsWithIds.put(R.id.PartNoEt, 11);
        sViewsWithIds.put(R.id.ly_helpers_1, 12);
        sViewsWithIds.put(R.id.UOMET, 13);
        sViewsWithIds.put(R.id.WeightET, 14);
        sViewsWithIds.put(R.id.UOMEt, 15);
        sViewsWithIds.put(R.id.EffectiveDateET, 16);
        sViewsWithIds.put(R.id.ProcessGroupACT, 17);
        sViewsWithIds.put(R.id.ProcessNameACT, 18);
        sViewsWithIds.put(R.id.AverageMaterialSupplyET, 19);
        sViewsWithIds.put(R.id.RateNoET, 20);
        sViewsWithIds.put(R.id.RateKGET, 21);
        sViewsWithIds.put(R.id.BillingInACT, 22);
        sViewsWithIds.put(R.id.FreightACT, 23);
        sViewsWithIds.put(R.id.ProcessChargeET, 24);
        sViewsWithIds.put(R.id.FreightRateET, 25);
        sViewsWithIds.put(R.id.inwardPendingDetail_LL, 26);
        sViewsWithIds.put(R.id.temperingPendingDetails_detail_rv, 27);
        sViewsWithIds.put(R.id.llbtn, 28);
        sViewsWithIds.put(R.id.CreateCustomerRatesBT, 29);
        sViewsWithIds.put(R.id.saveBT, 30);
        sViewsWithIds.put(R.id.findBT, 31);
        sViewsWithIds.put(R.id.modifyBT, 32);
        sViewsWithIds.put(R.id.clearBT, 33);
    }
    // views
    @NonNull
    private final android.widget.RelativeLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentCustomerWiseItemRateDetailsBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 34, sIncludes, sViewsWithIds));
    }
    private FragmentCustomerWiseItemRateDetailsBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (androidx.appcompat.widget.AppCompatEditText) bindings[19]
            , (android.widget.AutoCompleteTextView) bindings[22]
            , (android.widget.Button) bindings[29]
            , (android.widget.AutoCompleteTextView) bindings[7]
            , (android.widget.AutoCompleteTextView) bindings[5]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[16]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[3]
            , (android.widget.AutoCompleteTextView) bindings[23]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[25]
            , (android.widget.AutoCompleteTextView) bindings[10]
            , (android.widget.AutoCompleteTextView) bindings[8]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[11]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[24]
            , (android.widget.AutoCompleteTextView) bindings[17]
            , (android.widget.AutoCompleteTextView) bindings[18]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[21]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[20]
            , (android.widget.AutoCompleteTextView) bindings[13]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[15]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[14]
            , (android.widget.Button) bindings[33]
            , (android.widget.Button) bindings[31]
            , (android.widget.LinearLayout) bindings[26]
            , (android.widget.LinearLayout) bindings[28]
            , (android.widget.AutoCompleteTextView) bindings[2]
            , (android.widget.LinearLayout) bindings[9]
            , (android.widget.LinearLayout) bindings[12]
            , (android.widget.LinearLayout) bindings[1]
            , (android.widget.LinearLayout) bindings[6]
            , (android.widget.LinearLayout) bindings[4]
            , (android.widget.Button) bindings[32]
            , (android.widget.Button) bindings[30]
            , (androidx.recyclerview.widget.RecyclerView) bindings[27]
            );
        this.mboundView0 = (android.widget.RelativeLayout) bindings[0];
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
        if (BR.customerWiseItemAndRateDetailsViewModel == variableId) {
            setCustomerWiseItemAndRateDetailsViewModel((com.pg.crm.ui.customerwiseitemandratedetils.CustomerWiseItemAndRateDetailsViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setCustomerWiseItemAndRateDetailsViewModel(@Nullable com.pg.crm.ui.customerwiseitemandratedetils.CustomerWiseItemAndRateDetailsViewModel CustomerWiseItemAndRateDetailsViewModel) {
        this.mCustomerWiseItemAndRateDetailsViewModel = CustomerWiseItemAndRateDetailsViewModel;
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
        flag 0 (0x1L): customerWiseItemAndRateDetailsViewModel
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}