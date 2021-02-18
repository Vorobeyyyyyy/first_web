var modal = document.getElementById("avatar_change_modal");
var btn = document.getElementById("avatar_button");
var span = document.getElementById("close_avatar_change");
btn.onclick = function () {
  modal.style.display = "block";
}
span.onclick = function () {
  modal.style.display = "none";
}
window.onclick = function (event) {
  if (event.target == modal) {
    modal.style.display = "none";
  }
}