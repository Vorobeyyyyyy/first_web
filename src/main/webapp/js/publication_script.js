const IMG_REGEXP = /\$\$\$IMG=(.+)\$\$\$/
const VIDEO_REGEXP = /\$\$\$VIDEO=(.+)\$\$\$/

const body = document.getElementsByClassName('news_body').item(0)
const raw = body.innerText
body.innerHTML = ''
const parts = raw.split('$$$END$$$')
parts.forEach(part => {
    let element = document.createElement('div')
    element.classList.add('body_holder')
    body.append(element);
    part = part.replace(IMG_REGEXP, function (str, path) {
        let imgElement = document.createElement('img')
        imgElement.src = srcPrefix.substring(0, srcPrefix.length - 2) + path
        imgElement.classList.add('body_image')
        element.append(imgElement)
        return ''
    })
    part = part.replace(VIDEO_REGEXP, function (str, path) {
        let iframeElement = document.createElement('iframe')
        iframeElement.width = '640'
        iframeElement.height = '360'
        iframeElement.allowFullscreen = true
        iframeElement.src = path
        iframeElement.classList.add('body_video')
        iframeElement.classList.add('centred')
        element.append(iframeElement)
        return ''
    })

    element.append(part)
})

if (isLogin === 'true') {
    const commends = document.getElementsByClassName('like_button')
    for (let i = 0; i < commends.length; i++) {
        commends[i].addEventListener('click', function (e) {
            const likeButton = e.currentTarget
            if (!likeButton.classList.contains('liked')) {
                postReq(likeCommand, "commend_id=" + likeButton.getAttribute('commendId') + "&command=like", function () {
                    setLiked(likeButton)
                }, function () {
                    alert('Error')
                })
            } else {
                postReq(likeCommand, "commend_id=" + likeButton.getAttribute('commendId') + "&command=unlike", function () {
                    setUnliked(likeButton)
                }, function () {
                    alert('Error')
                })
            }
        })
    }
}

function postReq(url, data, callbackOk, callbackNotOK) {
    const xhr = new XMLHttpRequest();
    xhr.open("POST", url);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                callbackOk()
            } else {
                callbackNotOK()
            }
        }
    };
    xhr.send(data);
}

function setLiked(likeButton) {
    likeButton.classList.add('liked')
    const likeCount = likeButton.querySelector('.like_count')
    likeCount.innerText = parseInt(likeCount.innerText) + 1
}

function setUnliked(likeButton) {
    likeButton.classList.remove('liked')
    const likeCount = likeButton.querySelector('.like_count')
    likeCount.innerText = parseInt(likeCount.innerText) - 1
}