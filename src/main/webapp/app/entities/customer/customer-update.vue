<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="jhip01App.customer.home.createOrEditLabel"
          data-cy="CustomerCreateUpdateHeading"
          v-text="$t('jhip01App.customer.home.createOrEditLabel')"
        >
          Create or edit a Customer
        </h2>
        <div>
          <div class="form-group" v-if="customer.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="customer.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhip01App.customer.name')" for="customer-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="customer-name"
              data-cy="name"
              :class="{ valid: !$v.customer.name.$invalid, invalid: $v.customer.name.$invalid }"
              v-model="$v.customer.name.$model"
              required
            />
            <div v-if="$v.customer.name.$anyDirty && $v.customer.name.$invalid">
              <small class="form-text text-danger" v-if="!$v.customer.name.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhip01App.customer.enName')" for="customer-enName">En Name</label>
            <input
              type="text"
              class="form-control"
              name="enName"
              id="customer-enName"
              data-cy="enName"
              :class="{ valid: !$v.customer.enName.$invalid, invalid: $v.customer.enName.$invalid }"
              v-model="$v.customer.enName.$model"
              required
            />
            <div v-if="$v.customer.enName.$anyDirty && $v.customer.enName.$invalid">
              <small class="form-text text-danger" v-if="!$v.customer.enName.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhip01App.customer.fullName')" for="customer-fullName">Full Name</label>
            <input
              type="text"
              class="form-control"
              name="fullName"
              id="customer-fullName"
              data-cy="fullName"
              :class="{ valid: !$v.customer.fullName.$invalid, invalid: $v.customer.fullName.$invalid }"
              v-model="$v.customer.fullName.$model"
              required
            />
            <div v-if="$v.customer.fullName.$anyDirty && $v.customer.fullName.$invalid">
              <small class="form-text text-danger" v-if="!$v.customer.fullName.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
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
            :disabled="$v.customer.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./customer-update.component.ts"></script>
