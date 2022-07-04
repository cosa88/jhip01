<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="customerGroup">
        <h2 class="jh-entity-heading" data-cy="customerGroupDetailsHeading">
          <span v-text="$t('jhip01App.customerGroup.detail.title')">CustomerGroup</span> {{ customerGroup.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('jhip01App.customerGroup.name')">Name</span>
          </dt>
          <dd>
            <span>{{ customerGroup.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhip01App.customerGroup.enName')">En Name</span>
          </dt>
          <dd>
            <span>{{ customerGroup.enName }}</span>
          </dd>
          <dt>
            <span v-text="$t('jhip01App.customerGroup.customer')">Customer</span>
          </dt>
          <dd>
            <div v-if="customerGroup.customer">
              <router-link :to="{ name: 'CustomerView', params: { customerId: customerGroup.customer.id } }">{{
                customerGroup.customer.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="customerGroup.id"
          :to="{ name: 'CustomerGroupEdit', params: { customerGroupId: customerGroup.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./customer-group-details.component.ts"></script>
