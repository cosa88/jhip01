<template>
  <div>
    <b-dropdown-item to="/customer">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.customer')">Customer</span>
    </b-dropdown-item>
    <b-dropdown-item to="/customer-group">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.customerGroup')">Customer Group</span>
    </b-dropdown-item>
    <b-dropdown-item to="/project">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.project')">Project</span>
    </b-dropdown-item>
    <!-- jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here -->
  </div>
</template>

<script lang="ts" src="./entities-menu.component.ts"></script>
