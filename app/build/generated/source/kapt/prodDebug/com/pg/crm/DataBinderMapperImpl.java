package com.pg.crm;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.pg.crm.databinding.ActivityLoginBindingImpl;
import com.pg.crm.databinding.FragmentBatchScheduleBindingImpl;
import com.pg.crm.databinding.FragmentCustomerWiseItemRateDetailsBindingImpl;
import com.pg.crm.databinding.FragmentHardeningProcessBindingImpl;
import com.pg.crm.databinding.FragmentHardeningVacuumBindingImpl;
import com.pg.crm.databinding.FragmentMaterialInwardBindingImpl;
import com.pg.crm.databinding.FragmentWeightmentBindingImpl;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.RuntimeException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBinderMapperImpl extends DataBinderMapper {
  private static final int LAYOUT_ACTIVITYLOGIN = 1;

  private static final int LAYOUT_FRAGMENTBATCHSCHEDULE = 2;

  private static final int LAYOUT_FRAGMENTCUSTOMERWISEITEMRATEDETAILS = 3;

  private static final int LAYOUT_FRAGMENTHARDENINGPROCESS = 4;

  private static final int LAYOUT_FRAGMENTHARDENINGVACUUM = 5;

  private static final int LAYOUT_FRAGMENTMATERIALINWARD = 6;

  private static final int LAYOUT_FRAGMENTWEIGHTMENT = 7;

  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(7);

  static {
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pg.crm.R.layout.activity_login, LAYOUT_ACTIVITYLOGIN);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pg.crm.R.layout.fragment_batch_schedule, LAYOUT_FRAGMENTBATCHSCHEDULE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pg.crm.R.layout.fragment_customer_wise_item_rate_details, LAYOUT_FRAGMENTCUSTOMERWISEITEMRATEDETAILS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pg.crm.R.layout.fragment_hardening_process, LAYOUT_FRAGMENTHARDENINGPROCESS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pg.crm.R.layout.fragment_hardening_vacuum, LAYOUT_FRAGMENTHARDENINGVACUUM);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pg.crm.R.layout.fragment_material_inward, LAYOUT_FRAGMENTMATERIALINWARD);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pg.crm.R.layout.fragment_weightment, LAYOUT_FRAGMENTWEIGHTMENT);
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = view.getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
        case  LAYOUT_ACTIVITYLOGIN: {
          if ("layout/activity_login_0".equals(tag)) {
            return new ActivityLoginBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_login is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTBATCHSCHEDULE: {
          if ("layout/fragment_batch_schedule_0".equals(tag)) {
            return new FragmentBatchScheduleBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_batch_schedule is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTCUSTOMERWISEITEMRATEDETAILS: {
          if ("layout/fragment_customer_wise_item_rate_details_0".equals(tag)) {
            return new FragmentCustomerWiseItemRateDetailsBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_customer_wise_item_rate_details is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTHARDENINGPROCESS: {
          if ("layout/fragment_hardening_process_0".equals(tag)) {
            return new FragmentHardeningProcessBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_hardening_process is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTHARDENINGVACUUM: {
          if ("layout/fragment_hardening_vacuum_0".equals(tag)) {
            return new FragmentHardeningVacuumBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_hardening_vacuum is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTMATERIALINWARD: {
          if ("layout/fragment_material_inward_0".equals(tag)) {
            return new FragmentMaterialInwardBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_material_inward is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTWEIGHTMENT: {
          if ("layout/fragment_weightment_0".equals(tag)) {
            return new FragmentWeightmentBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_weightment is invalid. Received: " + tag);
        }
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
    if(views == null || views.length == 0) {
      return null;
    }
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = views[0].getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
      }
    }
    return null;
  }

  @Override
  public int getLayoutId(String tag) {
    if (tag == null) {
      return 0;
    }
    Integer tmpVal = InnerLayoutIdLookup.sKeys.get(tag);
    return tmpVal == null ? 0 : tmpVal;
  }

  @Override
  public String convertBrIdToString(int localId) {
    String tmpVal = InnerBrLookup.sKeys.get(localId);
    return tmpVal;
  }

  @Override
  public List<DataBinderMapper> collectDependencies() {
    ArrayList<DataBinderMapper> result = new ArrayList<DataBinderMapper>(1);
    result.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
    return result;
  }

  private static class InnerBrLookup {
    static final SparseArray<String> sKeys = new SparseArray<String>(8);

    static {
      sKeys.put(0, "_all");
      sKeys.put(1, "batchSchViewModel");
      sKeys.put(2, "customerWiseItemAndRateDetailsViewModel");
      sKeys.put(3, "hardeningProcessViewModel");
      sKeys.put(4, "hardeningVacuumViewModel");
      sKeys.put(5, "loginViewModel");
      sKeys.put(6, "materialInwardViewModel");
      sKeys.put(7, "weightmentViewModel");
    }
  }

  private static class InnerLayoutIdLookup {
    static final HashMap<String, Integer> sKeys = new HashMap<String, Integer>(7);

    static {
      sKeys.put("layout/activity_login_0", com.pg.crm.R.layout.activity_login);
      sKeys.put("layout/fragment_batch_schedule_0", com.pg.crm.R.layout.fragment_batch_schedule);
      sKeys.put("layout/fragment_customer_wise_item_rate_details_0", com.pg.crm.R.layout.fragment_customer_wise_item_rate_details);
      sKeys.put("layout/fragment_hardening_process_0", com.pg.crm.R.layout.fragment_hardening_process);
      sKeys.put("layout/fragment_hardening_vacuum_0", com.pg.crm.R.layout.fragment_hardening_vacuum);
      sKeys.put("layout/fragment_material_inward_0", com.pg.crm.R.layout.fragment_material_inward);
      sKeys.put("layout/fragment_weightment_0", com.pg.crm.R.layout.fragment_weightment);
    }
  }
}
