<script>
import { Pie } from "vue-chartjs";

export default {
  extends: Pie,
  mounted() {
    this.renderChart(
      {
        labels: ["Normal", "Critical"],
        datasets: [
          {
            backgroundColor: ['#2b88e5','#fa5b5a'],
            data: [40, 20]
          }
        ]
      },
      { responsive: true, maintainAspectRatio: false }
    );
  }
};
</script>