/**
 * BURGER MENU
 * **/
document.addEventListener("DOMContentLoaded", function() {
  const dropdownLinks = document.querySelectorAll('.dropdown-toggle');
  const nav = document.querySelector('.nav');
  const burgerMenu = document.getElementById('burger-menu');

  function handleClick(event) {
      if (window.innerWidth <= 1023) {
          event.preventDefault();

          const menu = this.nextElementSibling;

          if (menu.style.display === 'block') {
              menu.style.display = 'none';
              this.classList.remove('open');
          } else {
              document.querySelectorAll('.dropdown-content').forEach(content => {
                  content.style.display = 'none';
                  content.previousElementSibling.classList.remove('open');
              });
              menu.style.display = 'block';
              this.classList.add('open');
          }
      }
  }

  function handleMouseEnter() {
      if (window.innerWidth > 1023) {
          this.nextElementSibling.style.display = 'block';
          this.classList.add('open');
      }
  }

  function handleMouseLeave() {
      if (window.innerWidth > 1023) {
          this.nextElementSibling.style.display = 'none';
          this.classList.remove('open');
      }
  }

  // Toggle nav menu for smaller screens
  if (burgerMenu) {
      burgerMenu.addEventListener('click', function() {
          nav.classList.toggle('show');
      });
  }

  // Close the dropdown menu if the user clicks outside of it
  window.addEventListener('click', function(event) {
      if (!event.target.closest('.dropdown')) {
          document.querySelectorAll('.dropdown-content').forEach(content => {
              content.style.display = 'none';
              content.previousElementSibling.classList.remove('open');
          });
      }
  });

  // Event listeners for dropdown toggles
  dropdownLinks.forEach(link => {
      link.addEventListener('click', handleClick);
      link.addEventListener('mouseenter', handleMouseEnter);
      link.addEventListener('mouseleave', handleMouseLeave);
      link.nextElementSibling.addEventListener('mouseenter', function() {
          if (window.innerWidth > 1023) {
              this.style.display = 'block';
              link.classList.add('open');
          }
      });
      link.nextElementSibling.addEventListener('mouseleave', function() {
          if (window.innerWidth > 1023) {
              this.style.display = 'none';
              link.classList.remove('open');
          }
      });
  });

  // Function to update display based on window width
  function updateDisplay() {
      if (window.innerWidth > 1023) {
          nav.classList.remove('show');
          document.querySelectorAll('.dropdown-content').forEach(content => {
              content.style.display = 'none';
              content.previousElementSibling.classList.remove('open');
          });
      }
  }

  // Update display on window resize
  window.addEventListener('resize', updateDisplay);

  // Initial update display on load
  updateDisplay();
});




/**
 * FAQ SHOWING AND HIDING AV SECTIONS
 * **/
document.addEventListener("DOMContentLoaded", function() {
  // Wyświetlenie sekcji "claims-and-accidents" przy załadowaniu strony
  document.getElementById("claims-and-accidents").style.display = "block";

  // Dodanie klasy "active" do odpowiedniego elementu nawigacji
  var faqNavLines = document.querySelectorAll(".faq-nav-line");

  faqNavLines.forEach(function(element) {
    if (element.getAttribute("onclick").includes("faq-claims-and-accident")) {
      element.classList.add("active");
    }
  });

  // Dodanie obsługi kliknięcia dla każdego elementu FAQ nawigacji
  faqNavLines.forEach(function(element) {
    element.addEventListener("click", function() {
      faqNavLines.forEach(function(el) {
        el.classList.remove("active"); // Usuwamy klasę "active" ze wszystkich elementów .faq-nav-line
      });
      element.classList.add("active"); // Dodajemy klasę "active" do klikniętego elementu
    });
  });

  // Ukrycie wszystkich elementów .faq-description-content
  var faqContents = document.querySelectorAll(".faq-description-content");
  faqContents.forEach(function(content) {
    content.style.display = "none";
  });

  // Dodanie obsługi kliknięcia dla każdego elementu .faq-description-topic
  var faqTopics = document.querySelectorAll(".faq-description-topic");
  faqTopics.forEach(function(topic) {
    topic.addEventListener("click", function() {
      // Znalezienie następnego elementu .faq-description-content po klikniętym elemencie .faq-description-topic
      var nextContent = topic.nextElementSibling;
      if (nextContent && nextContent.classList.contains("faq-description-content")) {
        // Przełączenie widoczności elementu .faq-description-content
        if (nextContent.style.display === "block") {
          nextContent.style.display = "none";
          // Obrót strzałki w lewo
          topic.querySelector("img").classList.remove("rotate");
        } else {
          nextContent.style.display = "block";
          // Obrót strzałki w prawo
          topic.querySelector("img").classList.add("rotate");
        }
      }
    });
  });
});

function toggleSection(sectionClass) {
  var sections = document.getElementsByClassName("faq-hide-section");
  for (var i = 0; i < sections.length; i++) {
    sections[i].style.display = "none";
  }
  var section = document.getElementsByClassName(sectionClass)[0];
  section.style.display = "block";
}


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
              card.style.display = 'flex';
          });
      } else {
          // W przeciwnym razie, filtruj karty na podstawie wybranych opcji
          carCards.forEach(function(card) {
              const carClass = card.getAttribute('data-car-class');
              const carFeature = card.getAttribute('data-car-feature');
              const isAutomatic = selectedFilters.includes('automat');
              const isManual = selectedFilters.includes('manual');
              const shouldDisplay = isClassSelected ? (selectedFilters.includes(carClass) && ((!isAutomatic && !isManual) || (carFeature === 'automat' && isAutomatic) || (carFeature === 'manual' && isManual))) : (carFeature === 'automat' && isAutomatic) || (carFeature === 'manual' && isManual);
              card.style.display = shouldDisplay ? 'flex' : 'none';
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
  

/**
 * SET CURRENT YEAR IN FOOTER
 * **/
var currentDate = new Date();
var currentYear = currentDate.getFullYear();
document.getElementById("currentYear").innerHTML = currentYear;



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



  
  