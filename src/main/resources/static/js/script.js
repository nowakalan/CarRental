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
      const isClassSelected = selectedFilters.some(filter => filter !== 'automat' && filter !== 'manual');

      if (selectedFilters.length === 0) {
          // Jeśli nie został wybrany żaden filtr, wyświetl wszystkie karty
          carCards.forEach(function(card) {
              card.style.display = 'block';
          });
      } else {
          // W przeciwnym razie, filtruj karty na podstawie wybranych opcji
          carCards.forEach(function(card) {
              const carClass = card.getAttribute('data-car-class');
              const carFeature = card.getAttribute('data-car-feature');
              const isAutomatic = selectedFilters.includes('automat');
              const isManual = selectedFilters.includes('manual');
              const shouldDisplay = isClassSelected ? (selectedFilters.includes(carClass) && ((!isAutomatic && !isManual) || (carFeature === 'automat' && isAutomatic) || (carFeature === 'manual' && isManual))) : (carFeature === 'automat' && isAutomatic) || (carFeature === 'manual' && isManual);
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


/**
 * FILTER DROP OFF LOCATION BY CHOSEN RENTAL
 * **/
function filterBranchesByRental(branches, rentalId) {
    console.log('Rental ID to:', rentalId);
    return branches.filter(branch => branch.rental === rentalId);
}

// Funkcja aktualizująca opcje drop-off location na podstawie wybranej lokalizacji pick-up location
function updateDropOffLocation(pickUpBranchId) {
    // Pobieranie danych o wszystkich oddziałach
    const allBranches = Array.from(document.querySelectorAll('#currentPickUpBranchId option')).map(option => ({
        value: option.value,
        text: option.textContent,
        rental: option.getAttribute('data-rental')
    }));
    console.log('Wszystkie oddziały:', allBranches);

    // Sprawdzenie, czy dane zostały poprawnie pobrane
    if (allBranches.length === 0) {
        console.log('Brak danych o oddziałach.');
        return; // Przerwij funkcję, jeśli brak danych
    }

    // Wyciągnięcie identyfikatora wypożyczalni z pickUpBranchId
    const pickUpRentalId = allBranches.find(branch => branch.value === pickUpBranchId).rental;
    console.log('PickUpRentalId:', pickUpRentalId);

    // Filtrowanie oddziałów na podstawie wynajmu
    const filteredBranches = filterBranchesByRental(allBranches, pickUpRentalId);
    console.log('Filtrowane oddziały:', filteredBranches);

    // Aktualizacja opcji drop-off location
    const dropOffLocationSelect = document.getElementById('currentDropOffBranchId');
    dropOffLocationSelect.innerHTML = ''; // Usunięcie wszystkich dotychczasowych opcji

    filteredBranches.forEach(branch => {
        const option = document.createElement('option');
        option.value = branch.value;
        option.textContent = branch.text;
        dropOffLocationSelect.appendChild(option);
    });

    // Ustawienie drop-off location na pick-up location
    dropOffLocationSelect.value = pickUpBranchId;
}

// Wywołanie funkcji po załadowaniu strony
document.addEventListener('DOMContentLoaded', function() {
    const pickUpBranchId = document.getElementById('currentPickUpBranchId').value;
    updateDropOffLocation(pickUpBranchId);
});

// Wywołanie funkcji po zmianie pickUpLocation
document.getElementById('currentPickUpBranchId').addEventListener('change', function () {
    console.log('Zmiana wyboru pick-up location:', this.value);
    updateDropOffLocation(this.value);
});



  
  