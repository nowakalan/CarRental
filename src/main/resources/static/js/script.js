/**
 * BURGER MENU
 * **/
document.getElementById('burger-menu').addEventListener('click', function () {
  document.querySelector('.nav').classList.toggle('show');
});
document.addEventListener('DOMContentLoaded', function () {
  if (window.innerWidth <= 767) {
    var dropdowns = document.querySelectorAll('.nav .dropdown');

    dropdowns.forEach(function (dropdown) {
      dropdown.addEventListener('click', function () {
        var submenu = this.querySelector('.submenu');
        if (submenu) {
          // Schowaj wszystkie otwarte podmenu
          var openSubmenus = document.querySelectorAll('.submenu.show');
          openSubmenus.forEach(function (openSubmenu) {
            openSubmenu.classList.remove('show');
          });

          // Pokaż/ukryj aktualne podmenu
          submenu.classList.toggle('show');
        }
      });

      // Dodaj obsługę zamykania podmenu po kliknięciu poza nim
      document.addEventListener('click', function (event) {
        var isClickInside = dropdown.contains(event.target);
        if (!isClickInside) {
          var openSubmenu = document.querySelector('.submenu.show');
          if (openSubmenu) {
            openSubmenu.classList.remove('show');
          }
        }
      });
    });
  }
});

/**
 * FILTER CAR CLASS
 * **/
      
document.addEventListener("DOMContentLoaded", function() {
  const filterCheckboxes = document.querySelectorAll('.filter-checkbox');
  const carCards = document.querySelectorAll('.car-card');

  filterCheckboxes.forEach(function(checkbox) {
      checkbox.addEventListener('change', function() {
          filterCars();
      });
  });

  function filterCars() {
      const selectedFilters = getSelectedFilters();

      if (selectedFilters.length === 0) {
          // Jeśli żaden filtr nie jest zaznaczony, wyświetl wszystkie karty
          carCards.forEach(function(card) {
              card.style.display = 'block';
          });
      } else {
          // W przeciwnym razie, filtruj karty na podstawie wybranych opcji
          carCards.forEach(function(card) {
              const carClass = card.getAttribute('data-car-class');
              const carFeature = card.getAttribute('data-car-feature');
              const shouldDisplay = selectedFilters.includes(carClass) || selectedFilters.includes(carFeature);
              card.style.display = shouldDisplay ? 'block' : 'none';
          });
      }
  }

  function getSelectedFilters() {
      const selectedFilters = [];
      filterCheckboxes.forEach(function(checkbox) {
          if (checkbox.checked) {
              selectedFilters.push(checkbox.id);
          }
      });
      return selectedFilters;
  }
});


/**
 * SET TIMEOUT FOR MESSAGES
 * **/
    setTimeout(function() {
        var messages = document.querySelectorAll('.message');
        messages.forEach(function(message) {
            message.classList.add('hidden');
        });
    }, 5000);


/** SHOW/HIDE FILTERS SMALL SCREAN **/
document.addEventListener("DOMContentLoaded", function() {
  document.getElementById('filters-dropdown').addEventListener('click', function () {
    document.querySelector('.filters').classList.toggle('show');
});
});



/**
 * VALIDATE DATE / TIME
 * **/
function validateDateTime() {
    var pickUpDate = new Date(document.getElementById('pickUpDate').value);
    var pickUpTime = document.getElementById('pickUpTime').value;
    var dropOffDate = new Date(document.getElementById('dropOffDate').value);
    var dropOffTime = document.getElementById('dropOffTime').value;

    var pickUpDateTime = new Date(pickUpDate.getFullYear(), pickUpDate.getMonth(), pickUpDate.getDate(), pickUpTime.split(':')[0], pickUpTime.split(':')[1]);
    var dropOffDateTime = new Date(dropOffDate.getFullYear(), dropOffDate.getMonth(), dropOffDate.getDate(), dropOffTime.split(':')[0], dropOffTime.split(':')[1]);

    var currentDateTime = new Date();

    if (pickUpDateTime < currentDateTime) {
        alert("Pick-up date/time cannot be earlier than the current date/time.");
        return false;
    }

    if (dropOffDateTime <= pickUpDateTime) {
        alert("Drop-off date/time cannot be earlier than the pick-up date/time.");
        return false;
    }

    return true;
}



  
  