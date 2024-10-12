package com.pg.crm.model

import com.google.gson.annotations.SerializedName

data class CustomerInformationModel(
    @SerializedName("customerCreationDate") var customerCreationDate: String? = null,
    @SerializedName("empCreatedID") var empCreatedID: String? = null,
    @SerializedName("customerCategory") var customerCategory: String? = null,
    @SerializedName("salesAreaCode") var salesAreaCode: Int? = null,
    @SerializedName("dealerDistCode") var dealerDistCode: Int? = null,
    @SerializedName("customerOrgID") var customerOrgID: Int? = null,
    @SerializedName("customerOrganisationName") var customerOrganisationName: String? = null,
    @SerializedName("customerOrganisationLegalStatus") var customerOrganisationLegalStatus: String? = null,
    @SerializedName("customerOrganisationAddress") var customerOrganisationAddress: String? = null,
    @SerializedName("countryCode") var countryCode: Int? = null,
    @SerializedName("stateCode") var stateCode: Int? = null,
    @SerializedName("districtCode") var districtCode: Int? = null,
    @SerializedName("placeCode") var placeCode: Int? = null,
    @SerializedName("customerPINCode") var customerPINCode: Int? = null,
    @SerializedName("customerManagementName") var customerManagementName: String? = null,
    @SerializedName("customerManagementDesignation") var customerManagementDesignation: String? = null,
    @SerializedName("customerManagementCN") var customerManagementCN: Long? = null,
    @SerializedName("customerManagementMID") var customerManagementMID: String? = null,
    @SerializedName("customerManagementAltMID") var customerManagementAltMID: String? = null,
    @SerializedName("customerOrgContactPerson") var customerOrgContactPerson: String? = null,
    @SerializedName("customerOrgContactPersonDesignation") var customerOrgContactPersonDesignation: String? = null,
    @SerializedName("customerOrgContactPersonCN") var customerOrgContactPersonCN: Long? = null,
    @SerializedName("customerOrgContactPersonMID") var customerOrgContactPersonMID: String? = null,
    @SerializedName("customerOrganisationOfficeNumber") var customerOrganisationOfficeNumber: Long? = null,
    @SerializedName("customerOrganisationActivity") var customerOrganisationActivity: String? = null,
    @SerializedName("customerOrganisationGSTN") var customerOrganisationGSTN: String? = null,
    @SerializedName("customerOrganisationWebsite") var customerOrganisationWebsite: String? = null,
    @SerializedName("customerOrganisationLicenseNo") var customerOrganisationLicenseNo: String? = null
)
