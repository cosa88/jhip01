<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="jhip01App.customerGroup.home.createOrEditLabel"
          data-cy="CustomerGroupCreateUpdateHeading"
          v-text="$t('jhip01App.customerGroup.home.createOrEditLabel')"
        >
          Create or edit a CustomerGroup
        </h2>
        <div>
          <div class="form-group" v-if="customerGroup.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="customerGroup.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhip01App.customerGroup.name')" for="customer-group-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="customer-group-name"
              data-cy="name"
              :class="{ valid: !$v.customerGroup.name.$invalid, invalid: $v.customerGroup.name.$invalid }"
              v-model="$v.customerGroup.name.$model"
              required
            />
            <div v-if="$v.customerGroup.name.$anyDirty && $v.customerGroup.name.$invalid">
              <small class="form-text text-danger" v-if="!$v.customerGroup.name.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhip01App.customerGroup.enName')" for="customer-group-enName">En Name</label>
            <input
              type="text"
              class="form-control"
              name="enName"
              id="customer-group-enName"
              data-cy="enName"
              :class="{ valid: !$v.customerGroup.enName.$invalid, invalid: $v.customerGroup.enName.$invalid }"
              v-model="$v.customerGroup.enName.$model"
              required
            />
            <div v-if="$v.customerGroup.enName.$anyDirty && $v.customerGroup.enName.$invalid">
              <small class="form-text text-danger" v-if="!$v.customerGroup.enName.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhip01App.customerGroup.customer')" for="customer-group-customer">Customer</label>
            <select class="form-control" id="customer-group-customer" data-cy="customer" name="customer" v-model="customerGroup.customer">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  customerGroup.customer && customerOption.id === customerGroup.customer.id ? customerGroup.customer : customerOption
                "
                v-for="customerOption in customers"
                :key="customerOption.id"
              >
                {{ customerOption.id }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.customerGroup.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./customer-group-update.component.ts"></script>
