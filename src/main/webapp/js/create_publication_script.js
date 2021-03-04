const mainImage = document.getElementById('main_image');
const newsBody = document.getElementById('news_body');

const uploadForm = document.getElementById('upload_form')
const fileSelect = document.getElementById('file_select')

const fileNameSpecifier = 'file_name='

const getImgPrefix = mainImage.getAttribute('src').substr(0, mainImage.getAttribute('src').lastIndexOf(fileNameSpecifier)) + fileNameSpecifier
console.log(getImgPrefix)

remapImg()
function remapImg() {
    let images = document.getElementsByClassName('changeable_img')
    for (let image of images) {
        image.onclick = function () {
            fileSelect.click()
            fileSelect.onchange = e => {
                fetch(uploadForm.action, {
                    method: uploadForm.method,
                    body: new FormData(uploadForm)
                }).then(res => {
                    if (res.ok) {
                        res.text().then(text => image.src = getImgPrefix + text)
                    }
                })
            }
        }
    }
}

submitButton = document.getElementById('submit_button')
hiddenForm = document.getElementById('hidden_form')

realTitle = document.getElementById('real_topic')
realImg = document.getElementById('main_image')

formTitle = document.getElementById('form_title')
formImg = document.getElementById('form_img')
formContent = document.getElementById('form_content')
formSummary = document.getElementById('form_summary')

submitButton.onclick = function () {
    let a = '';
    let nodes = newsBody.children
    for (let i = 0; i < nodes.length; i++) {
        let node = nodes[i]
        if (node.innerText !== null) {
            a += node.innerText
        }
        if (node !== nodes[nodes.length - 1]) {
            a += '$$$END$$$'
        }
    }
    formContent.innerText = a
    formSummary.innerText = newsBody.childNodes[1].innerText
    formTitle.innerText = realTitle.value
    formImg.value = extractImgPath(realImg.src)
    hiddenForm.submit()
}

function extractImgPath(fullPath) {
    return fullPath.substr(fullPath.lastIndexOf(fileNameSpecifier) + fileNameSpecifier.length)
}


const body = document.getElementsByClassName('news_body').item(0)
const raw = body.innerText
console.log(raw.length)
if (raw.length > 2) {
    body.innerHTML = ''
    const parts = raw.split('$$$END$$$')
    parts.forEach(part => {
        let element = document.createElement('div')
        element.classList.add('body_holder')
        body.append(element);
        element.append(part)
    })
}