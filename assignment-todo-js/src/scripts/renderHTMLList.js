const undo_list = document.querySelector("#todo-lists")
const done_list = document.querySelector("#completed-lists")

function getHTMLList(task, isCompleted) {
    console.log(task)
    return task
        .filter(task => task.completed === isCompleted)
        .reverse()
        .map(({id, name, completed}) =>
            `<li>
            <input id=${id} class="input-checkbox" type="checkbox" ${completed ? "checked" : ""}/>
            <label data-id=${id}> ${name} </label>
            <i class="bi-trash" data-id=${id}></i>
          </li>
            `
        )
        .join("")
}

export function renderList(tasks) {
    undo_list.innerHTML = getHTMLList(tasks, false)
    done_list.innerHTML = getHTMLList(tasks, true)

}
