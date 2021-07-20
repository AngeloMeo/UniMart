var offset = 5;
const size = 10;

if(window.addEventListener)
    window.addEventListener('scroll',scroll)

function scroll()
{
    let pos = Math.max(document.documentElement.scrollTop,document.body.scrollTop);

    if((pos + document.documentElement.clientHeight) >= document.documentElement.scrollHeight)
        getItems(size, offset);
}

function aggiornaOffset()
{
    offset += size;
}