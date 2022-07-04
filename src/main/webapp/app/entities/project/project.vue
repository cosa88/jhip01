<template>
  <div>
    <h2 id="page-heading" data-cy="ProjectHeading">
      <span v-text="$t('jhip01App.project.home.title')" id="project-heading">Projects</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('jhip01App.project.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'ProjectCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-project"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('jhip01App.project.home.createLabel')"> Create a new Project </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && projects && projects.length === 0">
      <span v-text="$t('jhip01App.project.home.notFound')">No projects found</span>
    </div>
    <div class="table-responsive" v-if="projects && projects.length > 0">
      <table class="table table-striped" aria-describedby="projects">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('jhip01App.project.name')">Name</span></th>
            <th scope="row"><span v-text="$t('jhip01App.project.customerGroup')">Customer Group</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="project in projects" :key="project.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'ProjectView', params: { projectId: project.id } }">{{ project.id }}</router-link>
            </td>
            <td>{{ project.name }}</td>
            <td>
              <div v-if="project.customerGroup">
                <router-link :to="{ name: 'CustomerGroupView', params: { customerGroupId: project.customerGroup.id } }">{{
                  project.customerGroup.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'ProjectView', params: { projectId: project.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'ProjectEdit', params: { projectId: project.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(project)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="jhip01App.project.delete.question" data-cy="projectDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-project-heading" v-text="$t('jhip01App.project.delete.question', { id: removeId })">
          Are you sure you want to delete this Project?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-project"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeProject()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./project.component.ts"></script>
