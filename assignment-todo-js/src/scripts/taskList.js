export let taskList = []

export function initialize_taskList(data) {
    taskList = data
}

export function add_task_to_taskList(newTask) {
    newTask.completed = false;
    taskList.push(newTask)
}
