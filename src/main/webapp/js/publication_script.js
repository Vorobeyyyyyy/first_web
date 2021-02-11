var text = document.getElementsByClassName('news_body')

var img_regexp_1 = /\%\%\%img=/
var img_regexp_2 =  /\%\%\%/

text123 = text.item(0).innerHTML


text123 = text123.replace(img_regexp_1, '<img src=')
text123 = text123.replace(img_regexp_2, '>')

text.item(0).innerHTML = text123