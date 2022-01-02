<script>
import { Bar } from "vue-chartjs";

export default {
  extends: Bar,
  props: {
    normalCount: {},
    criticalCount: {},
  },
  watch: {
    normalCount: function () {
      this.updateChart();
    },
  },
  mounted() {
    this.updateChart();
  },
  methods: {
    updateChart: function () {
      this.renderChart(
        {
          labels: [""],
          datasets: [
            {
              label: "Normal",
              backgroundColor: "#2b88e5",
              data: [this.normalCount],
            },
            {
              label: "Critical",
              backgroundColor: "#fa5b5a",
              data: [this.criticalCount],
            },
          ],
        },
        {
          responsive: true,
          maintainAspectRatio: false,
          scales: {
            yAxes: [
              {
                display: true,
                ticks: {
                  beginAtZero: true,
                  callback: function(value) {if (value % 1   === 0) {return value;}}
                },
              },
            ],
          },
        }
      );
    },
  },
};
</script>
