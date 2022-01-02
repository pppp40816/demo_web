<template>
  <div id="rackPanel">
   <MainMenu/>
    <el-row :gutter="10">
      <el-col :span="4">
        <ul id="columns">
          <li
            class="column"
            v-for="p in packages"
            :key="p.color"
            :dir="p.color"
            draggable="false"
          >
            <header>{{ p.content }}</header>
          </li>
        </ul>
      </el-col>
      <el-col :span="8">
        <div id="rackDiv" style="padding-top: 4px; text-align: -webkit-center">
          <table id="racktable">
            <tbody>
              <tr v-for="layer in layers" :key="layer.index">
                <td>{{ layer.index }}</td>
                <td :class="layer.class"></td>
                <td style="width: 20px; height: 19px" :class="layer.icon"></td>
              </tr>
            </tbody>
          </table>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import $ from "jquery";
import MainMenu from "./MainMenu.vue";

export default {
  components: {
    MainMenu
  },
  data() {
    return {
      packages: [
        { color: "red", content: "紅色包裹" },
        { color: "green", content: "綠色包裹" },
        { color: "yellow", content: "黃色包裹" },
        { color: "blue", content: "藍色包裹" },
        { color: "purple", content: "紫色包裹" },
      ],
      layers: [
        { index: "1", class: "emptyLayer", icon: "" },
        { index: "2", class: "emptyLayer", icon: "" },
        { index: "3", class: "emptyLayer", icon: "" },
        { index: "4", class: "emptyLayer", icon: "" },
        { index: "5", class: "emptyLayer", icon: "" },
        { index: "6", class: "emptyLayer", icon: "" },
        { index: "7", class: "emptyLayer", icon: "" },
        { index: "8", class: "emptyLayer", icon: "" },
        { index: "9", class: "emptyLayer", icon: "" },
        { index: "10", class: "emptyLayer", icon: "" },
      ],
    };
  },
  mounted() {
    this.addItemDragEvent();
     
    document.querySelectorAll(".emptyLayer").forEach((item) => {
      item.addEventListener("dragover", (event) => {
        event.preventDefault();
      });

      item.addEventListener("drop", (event) => {
        let packageKey = event.dataTransfer.getData("text/plain");
        let packageKeyJson = JSON.parse(packageKey);
        let color = packageKeyJson.color;
        if (color == undefined) {
          return;
        }
        console.log(color);
        if (!item.classList.contains("emptyLayer")) {
          this.notEmptyTip();
          return;
        }

        item.className = "";
        if (color == "red") {
          item.classList.add("redLayer");
        } else if (color == "green") {
          item.classList.add("greenLayer");
        } else if (color == "yellow") {
          item.classList.add("yellowLayer");
        } else if (color == "blue") {
          item.classList.add("blueLayer");
        } else if (color == "purple") {
          item.classList.add("purpleLayer");
        }

        let targetPackage;
        this.packages.forEach(function (each, index, object) {
          if (each.color === color) {
            targetPackage = each;
            object.splice(index, 1);
          }
        });

        $(item)[0].parentNode.children[2].classList.add("removeIcon");
        $($(item)[0].parentNode.children[2]).bind("click", (event) => {
          item.className = "";
          item.classList.add("emptyLayer");
          this.packages.push(targetPackage);
          event.target.className = "";
          $(event.target).unbind("click");
        });
      });
    });
  },
  methods: {
    notEmptyTip: function () {
      this.$message({
        type: "error",
        message: "此處已存放其他包裹",
        duration: 2000,
        showClose: true,
      });
    },
    dragStartEvent: function (event) {
      console.log($(event.target).attr("dir"));
      let packageKey = {
        key: "package",
      };
      packageKey["color"] = $(event.target).attr("dir");
      console.log(packageKey);
      event.dataTransfer.setData("text/plain", JSON.stringify(packageKey));
    },
    addItemDragEvent: function () {
      let dragitems = document.querySelectorAll("ul > li[draggable='false']");
      for (var i = 0; i < dragitems.length; i++) {
        let dragitem = dragitems[i];
        dragitem.setAttribute("draggable", true);
        dragitem.addEventListener("dragstart", this.dragStartEvent);
      }
    },
  },
  updated: function () {
    this.addItemDragEvent();
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

.emptyLayer {
  background-image: url(~@/assets/center.png);
  background-repeat: no-repeat;
  width: 240px;
  height: 19px;
}

.redLayer {
  background-image: url(~@/assets/red.png);
  background-repeat: no-repeat;
  width: 240px;
  height: 19px;
}

.blueLayer {
  background-image: url(~@/assets/blue.png);
  background-repeat: no-repeat;
  width: 240px;
  height: 19px;
}

.greenLayer {
  background-image: url(~@/assets/green.png);
  background-repeat: no-repeat;
  width: 240px;
  height: 19px;
}

.yellowLayer {
  background-image: url(~@/assets/yellow.png);
  background-repeat: no-repeat;
  width: 240px;
  height: 19px;
}

.purpleLayer {
  background-image: url(~@/assets/purple.png);
  background-repeat: no-repeat;
  width: 240px;
  height: 19px;
}

.removeIcon {
  background-image: url(~@/assets/close.png);
  background-repeat: no-repeat;
  width: 20px;
  height: 19px;
}

#columns {
  list-style-type: none;
}

.column {
  width: 162px;
  padding-bottom: 5px;
  padding-top: 5px;
  text-align: center;
  cursor: move;
}

.column header {
  height: 35px;
  width: 150px;
  color: black;
  background-color: #ccc;
  padding: 5px;
  border-bottom: 1px solid #ddd;
  border-radius: 10px;
  border: 2px solid #666666;
}

.column.dragElem {
  opacity: 0.4;
}
.column.over {
  border-top: 2px solid blue;
}
</style>
