<template>
  <div id="app">
          <el-menu mode="horizontal"
            default-active="1"  :router="true"
            class="el-menu-vertical-demo"
          >
            <el-menu-item index="/Dashboard">
              <i class="el-icon-pie-chart"></i>
              <span slot="title">儀錶板</span>
            </el-menu-item>
            <el-menu-item index="/Monitor">
              <i class="el-icon-view"></i>
              <span slot="title">
                效能監測
              </span>
            </el-menu-item>
            <el-menu-item index="/Rack">
              <i class="el-icon-notebook-2"></i>
              <span slot="title">貨櫃圖</span>
            </el-menu-item>
          </el-menu>
  </div>
</template>
<style>

</style>
