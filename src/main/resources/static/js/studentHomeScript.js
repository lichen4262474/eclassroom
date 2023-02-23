const codeBlock = function (index) {
  return (
    // '<div class="row g-3 m-3" id="row">' +
    '<div class="col">' +
    '<div class="card" style="width: 18rem">' +
    '<div class="card-body">' +
    '<h5 class="card-title pb-4" id="class-name' +
    index +
    '">Class Name</h5>' +
    '<div class="card-text">' +
    ' <p id="class-description' +
    index +
    '">Class Descripition</p>' +
    ' <p id="class-schedule' +
    index +
    '">Class Schedule</p> ' +
    ' <p id="class-zoom' +
    index +
    '">Zoom Link</p>' +
    "</div>" +
    '<a href="#" class="btn btn-primary">Enter Class</a>' +
    "</div>" +
    "</div>" +
    "</div>" +
    "</div>"
  );
};

addCard = function (index) {
  document.getElementById("class-card-container").innerHTML += codeBlock(index);
};
// use thymeleaf to load info

var myRequest = new XMLHttpRequest();
myRequest.open("GET", "/assets/data.json");

myRequest.onload = function () {
  var data = JSON.parse(myRequest.responseText);
  for (i = 0; i < data.length; i++) {
    addCard(i);
    document.getElementById("class-name" + i).innerText = data[i].className;
    document.getElementById("class-description" + i).innerText =
      data[i].classDescription;
    document.getElementById("class-schedule" + i).innerText =
      data[i].classSchedule;
    document.getElementById("class-zoom" + i).innerHTML =
      "<a href = data[i].classZoom>Zoom</a>";
  }
};
myRequest.send();
