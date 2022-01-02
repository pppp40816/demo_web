<template>
  <div id="SearchPanel">
   <MainMenu/>
   
  <el-main class="main" id="main">
    <el-row :gutter="10">
      <el-col :span="6">
        <label style="float: left">開始時間:</label>
        <input
          type="datetime-local"
          onkeydown="return false"
          id="search.startTime"
          v-model="search.startTime"
          style="width: 220px; float: left"
        />
      </el-col>
      <el-col :span="6">
        <label style="float: left">結束時間:</label>
        <input
          type="datetime-local"
          onkeydown="return false"
          id="search.endTime"
          v-model="search.endTime"
          style="width: 220px; float: left"
        />
      </el-col>
    </el-row>

    <el-row>
      <el-col :span="4">
        <button
          type="button"
          style="float: left"
          v-on:click="startSearch"
          class="btn btn-primary"
        >
          <i class="fa fa-search"></i>
          查詢
        </button>
      </el-col>
      <el-col :span="4"> </el-col>
    </el-row>
    <el-row :gutter="10">
      <el-col :span="12">
        <el-card>
          <div slot="header">
            <span>CPU健康度</span>
          </div>
          <VueSvgGauge
            class="mini-gauge"
            :min="0"
            :max="100"
            :start-angle="-120"
            :end-angle="120"
            :value="CpuHealth"
            :separator-step="0"
            :gauge-color="[
              { offset: 0, color: '#de3a21' },
              { offset: 50, color: '#f4c009' },
              { offset: 100, color: '#0b8c5a' },
            ]"
            :scale-interval="0"
            :inner-radius="65"
          >
            <div style="width: 100%; padding-top: 40%">
              <span>{{ CpuHealth }}%</span>
            </div>
          </VueSvgGauge>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <div slot="header">
            <span>CPU監測統計</span>
          </div>
          <BarChart :normalCount="CpuNormal" :criticalCount="CpuCritical" />
        </el-card>
      </el-col>

     <el-col :span="12">
        <el-card  style="margin-top: 10px;" >
          <div slot="header">
            <span>Memory健康度</span>
          </div>
          <VueSvgGauge
            class="mini-gauge"
            :min="0"
            :max="100"
            :start-angle="-120"
            :end-angle="120"
            :value="MemoryHealth"
            :separator-step="0"
            :gauge-color="[
              { offset: 0, color: '#de3a21' },
              { offset: 50, color: '#f4c009' },
              { offset: 100, color: '#0b8c5a' },
            ]"
            :scale-interval="0"
            :inner-radius="65"
          >
            <div style="width: 100%; padding-top: 40%">
              <span>{{ MemoryHealth }}%</span>
            </div>
          </VueSvgGauge>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card style="margin-top: 10px;" >
          <div slot="header">
            <span>Memory監測統計</span>
          </div>
          <BarChart :normalCount="MemoryNormal" :criticalCount="MemoryCritical" />
        </el-card>
      </el-col>
    </el-row>
    </el-main>
  </div>
</template>

<script>
import BarChart from "./BarChart.vue";
import MainMenu from "./MainMenu.vue";

export default {
  name: "DashBoardVue",
  components: {
    BarChart,
    MainMenu
  },
  created: function () {
    console.log("dashboard init");
  },
  data() {
    return {
      search: {
        uid: this.GLOBAL.uid,
        startTime: 0,
        endTime: 0,
      },
      CpuHealth: 0,
      CpuNormal: 0,
      CpuCritical: 0,
      MemoryHealth: 0,
      MemoryNormal: 0,
      MemoryCritical: 0,
    };
  },
  mounted() {
    let date = new Date();
    this.search.startTime = new Date(
      date.getTime() - date.getTimezoneOffset() * 60000 - 86400000
    )
      .toISOString()
      .slice(0, 16);
    this.search.endTime = new Date(
      date.getTime() - date.getTimezoneOffset() * 60000
    )
      .toISOString()
      .slice(0, 16);
  },
  methods: {
    startSearch: function () {
      let allow =
        this.checkDate(this.search.startTime) &&
        this.checkDate(this.search.endTime);
      console.log(allow);
      try {
        if (allow) {
          this.CpuHealth = 0;
          this.CpuNormal = 0;
          this.CpuCritical = 0;
          this.MemoryHealth = 0;
          this.MemoryNormal = 0;
          this.MemoryCritical = 0;
          this.$http
            .post(
              "http://localhost:9000/MonitorApi/getDashBoardData",
              this.search
            )
            .then((response) => {
              console.log(response.data);
              this.CpuHealth = response.data.cpuHealthRate;
              this.CpuNormal = response.data.cpuNormal;
              this.CpuCritical = response.data.cpuCritical;
              this.MemoryHealth = response.data.memoryHealthRate;
              this.MemoryNormal = response.data.memoryNormal;
              this.MemoryCritical = response.data.memoryCritical;
            });
        }
      } catch (e) {
        console.error(e);
      }
    },
    checkDate: function (target) {
      if (target == "") {
        this.$message({
          type: "error",
          message: "時間不可為空 ",
          duration: 2000,
          showClose: true,
        });
        return false;
      }
      return true;
    },
  },
  beforeDestroy() {},
};
</script>

<style>
#app {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

.el-row {
  margin-bottom: 20px;
  &:last-child {
    margin-bottom: 0;
  }
}
.el-col {
  border-radius: 4px;
}

.mini-gauge {
  height: 400px !important;
}
</style>
