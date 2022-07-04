<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="jhip01App.project.home.createOrEditLabel"
          data-cy="ProjectCreateUpdateHeading"
          v-text="$t('jhip01App.project.home.createOrEditLabel')"
        >
          Create or edit a Project
        </h2>
        <div>
          <div class="form-group" v-if="project.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="project.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhip01App.project.name')" for="project-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="project-name"
              data-cy="name"
              :class="{ valid: !$v.project.name.$invalid, invalid: $v.project.name.$invalid }"
              v-model="$v.project.name.$model"
              required
            />
            <div v-if="$v.project.name.$anyDirty && $v.project.name.$invalid">
              <small class="form-text text-danger" v-if="!$v.project.name.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('jhip01App.project.customerGroup')" for="project-customerGroup"
              >Customer Group</label
            >
            <select
              class="form-control"
              id="project-customerGroup"
              data-cy="customerGroup"
              name="customerGroup"
              v-model="project.customerGroup"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  project.customerGroup && customerGroupOption.id === project.customerGroup.id ? project.customerGroup : customerGroupOption
                "
                v-for="customerGroupOption in customerGroups"
                :key="customerGroupOption.id"
              >
                {{ customerGroupOption.id }}
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
            :disabled="$v.project.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./project-update.component.ts"></script>
