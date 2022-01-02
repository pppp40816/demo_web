<template>
  <div id="monitorPanel">
  <MainMenu/>
    <el-row :gutter="10">
      <el-col :span="4">
        <label style="float: left;" >CPU門檻值:</label>
        <input :disabled="isMonitor"
          type="text"
          id="threshold.cpu"
          v-model="threshold.cpu"
          style="width: 100px;float: left;"
        />
      </el-col>
      <el-col :span="4">
        <label style="float: left;" >Memory門檻值:</label>
        <input :disabled="isMonitor"
          type="text"
          id="threshold.memory"
          v-model="threshold.memory"
          style="width: 100px;float: left;"
        />
      </el-col>
    </el-row>

    <el-row >
      <el-col :span="4">
        <button
          type="button" style="float: left;"
          v-on:click="startMonitor"
          class="btn btn-primary"
          v-if="!isMonitor">
          <i class="fa fa-play"></i>
          開始監控
        </button>
        <button
          type="button" style="float: left;"
          v-on:click="stopMonitor"
          class="btn btn-danger"
          v-if="isMonitor">
          <i class="fa fa-stop"></i>
          停止監控
        </button>
      </el-col>
      <el-col :span="4">
      </el-col>
    </el-row>

    <LineChart :chartdata="chartdata" :Timelabels="Timelabels" />
    {{ MemoryUsage }}
  </div>
</template>

<script>
import LineChart from "./LineChart.vue";
import MainMenu from "./MainMenu.vue";

export default {
  name: "MonitorVue",
  components: {
    LineChart,
    MainMenu
  },
  created: function () {
    console.log("monitor init");
  },
  data() {
    return {
      threshold:{
        uid: this.GLOBAL.uid,
        memory: 0,
        cpu: 0
      },
      isMonitor: false,
      Timelabels: [],
      MemoryUsage: [],
      CpuUsage: [],
      chartdata: {
        labels: [],
        datasets: [
          {
            label: "Memory Usage",
            backgroundColor: "transparent",
            data: [],
            borderColor: "#fa5b5a",
            pointBackgroundColor: "#fa5b5a",
          },
          {
            label: "CPU Usage",
            backgroundColor: "transparent",
            data: [],
            borderColor: "#faad5a",
            pointBackgroundColor: "#faad5a",
          },
        ],
      },
    };
  },
  mounted() {
    //this.fetchData();
  },
  methods: {
    startMonitor: function () {
      let allow = this.checkNumber(this.threshold.cpu) && this.checkNumber(this.threshold.memory);
      if (allow) {
        this.Timelabels = [];
        this.MemoryUsage = [];
        this.CpuUsage = [];
        this.isMonitor = true;
        this.fetchData();
        this.$options.interval = setInterval(() => {
          this.fetchData();
        }, 10000);
      }
    },
    stopMonitor: function () {
      clearInterval(this.$options.interval);
      this.isMonitor = false;
    },
    fetchData: function () {
      try {
        this.$http
          .post("http://localhost:9000/MonitorApi/getPerformance",this.threshold )
          .then((response) => {
            console.log(response.data);
            this.Timelabels.push(response.data.timeLabel);
            this.MemoryUsage.push(response.data.memoryUsage);
            this.CpuUsage.push(response.data.cpuUsage);
            this.chartdata.labels = this.Timelabels;
            this.chartdata.datasets[0].data = this.MemoryUsage;
            this.chartdata.datasets[1].data = this.CpuUsage;
            //this.chartdata.push(response.data.timeLabel);
          });
      } catch (e) {
        console.error(e);
      }
    },
    checkNumber: function (target) {
      var numReg = /^[0-9]*$/;
      var numRe = new RegExp(numReg);
      if (!numRe.test(target)) {
        this.$message({
          type: "error",
          message: "請輸入整數字 ",
          duration: 2000,
          showClose: true,
        });
        return false;
      }
      if (target > 100 || target < 0) {
        this.$message({
          type: "error",
          message: "請輸入0~100 ",
          duration: 2000,
          showClose: true,
        });
        return false;
      }
      return true;
    },
  },
  beforeDestroy() {
    clearInterval(this.$options.interval);
  },
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
</style>
