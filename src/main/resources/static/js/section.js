function loadContent(page) {
    fetch(page)
        .then(response => response.text())
        .then(data => {
            document.getElementById('content').innerHTML = data;
        })
        .catch(error => {
            console.error('Error al cargar la página:', error);
            document.getElementById('content').innerHTML = '<p>Error al cargar el contenido.</p>';
        });
}

// Cargar contenido inicial
document.addEventListener('DOMContentLoaded', () => {
    // YA NO SE USA:

    /*
    var pageRoute = '';
    if (location.hash.length > 2) {
        pageRoute = location.hash.substring(1);
    }

    switch(pageRoute.toLowerCase()) {
        case 'employee':
            loadContent('/employee');
            break;
        case 'credential':
            loadContent('/credential');
            break;
        case 'client':
            loadContent('/client');
            break;
        case 'product':
            loadContent('/product');
            break;
        case 'category':
            loadContent('/category');
            break;
        case 'sale':
            loadContent('/sale');
            break;
        case 'quote':
            loadContent('/quote');
            break;
        default:
            loadContent('main.html');
            break;
    }*/
});
