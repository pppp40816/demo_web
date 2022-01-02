<script>
import { Line } from "vue-chartjs";

export default {
  extends: Line,
  props: {
    chartdata: {},
    Timelabels: {
      type: Array,
    }
  },
  mounted() {
    this.updateChart();
  },
  watch: {
    Timelabels: function () {
      this.updateChart();
    },
  },
  methods: {
    updateChart: function () {
      this.renderChart(this.chartdata, {
        responsive: true,
        maintainAspectRatio: false,
        scales: {
          xAxes: [
            {
              display: true,
              type: "time",
              time: {
                displayFormats: {
                  minute: "HH:mm:ss",
                },
                unit: "minute",
                format: "HH:mm:ss",
              },
              scaleLabel: {
                display: true,
                labelString: "Time",
              },
            },
          ],
          yAxes: [
            {
              beginAtZero: true,
              type: "linear",
              ticks: {
                min: 0,
                max: 100,
              },
            },
          ]
        },
      });
    },
  },
};
</script>
