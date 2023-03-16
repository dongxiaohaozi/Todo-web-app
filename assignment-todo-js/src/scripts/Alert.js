const alertMess = document.querySelector(".alert-error");

export function alert() {
    alertMess.style.display = "none"
}

export function showAlert() {
    alertMess.style.display = "block"
}
