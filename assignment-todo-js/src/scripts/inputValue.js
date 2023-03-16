const input = document.querySelector("#todo-input");

export function clearInput() {
    input.value = ''
}

export function getInputValue() {
    return input.value.trim();
}
