const modal = document.getElementById("avatar_change_modal");
const btn = document.getElementById("avatar_button");
const span = document.getElementById("close_avatar_change");
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

const avatarForm = document.getElementById('avatar_form')
const avatarForm2 = document.getElementById('avatar_form2')
const avatarPath = document.getElementById('avatar_path')

avatarForm.addEventListener('submit', e => {
  fetch(avatarForm.action, {
    method: avatarForm.method,
    body: new FormData(avatarForm)
  }).then((res) => {
    if (res.ok) {
      res.text().then(text => {
        avatarPath.value = text
        avatarForm2.submit()
      })

    }
  })
  e.preventDefault();
})

