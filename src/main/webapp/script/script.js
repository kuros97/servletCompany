document.addEventListener("DOMContentLoaded", function () {
  const form = document.getElementById("registrationForm");
  const inputs = form.querySelectorAll("input[required], select[required]");
  const generaCfButton = document.getElementById("generaCf");
  const cfInput = document.getElementById("cf");
  let cityCodes = [];


  // Quando il caricamento dei codici catastali è completato, aggiungo un event listener per il submit del form
form.addEventListener("submit", function (event) {
  event.preventDefault(); // Prevengo il comportamento predefinito del form (invio)

  let isValid = true;

  // Valido ogni input nel form
  inputs.forEach((input) => {
      if (!validazione(input)) {
          isValid = false;
      }
  });

  if (isValid) {
      // Se tutti i campi sono validi, invia il form
      this.submit();
      // Mostra un messaggio di successo
      alert("Registrazione completata con successo!");
  }
});


  // Caricamento dei dati dei codici catastali dal JSON
  fetch("https://mocki.io/v1/dc9ba0c2-260d-449f-a993-48f5618f066b")
      .then((response) => response.json())
      .then((data) => {
          // Itero su ogni città del JSON e memorizzo il nome e il codice catastale in un array (cityCodes)
          for (let i = 0; i < data.citta.length; i++) {
              let citta = {};
              citta.nomeCitta = data.citta[i].nomeCitta;
              citta.catastale = data.citta[i].catastale;
              cityCodes.push(citta);
          }
      })
      .then(() => {
          // Quando il caricamento dei codici catastali è completato, aggiungo un event listener per il submit del form
          form.addEventListener("submit", function (event) {
              event.preventDefault(); // Prevengo il comportamento predefinito del form (invio)
              let isValid = true;

              // Valido ogni input nel form
              inputs.forEach((input) => {
                  if (!validazione(input)) {
                      isValid = false;
                  }
              });

              if (isValid) {
                  alert("Registrazione completata con successo!"); // Mostro un messaggio di successo se il form è valido
              }
          });
      })
      .catch((error) => {
          console.error("Errore nel recupero dei codici catastali:", error); // Gestisco l'errore se il fetch fallisce
      });

  // Aggiungo un event listener per ogni input per validarlo in tempo reale
  inputs.forEach((input) => {
      input.addEventListener("input", () => validazione(input));
  });

  // Event listener per generare il codice fiscale
  generaCfButton.addEventListener("click", function () {
      const nome = document.getElementById("nome").value;
      const cognome = document.getElementById("cognome").value;
      const dataNascita = document.getElementById("dataNascita").value;
      const sesso = document.getElementById("sesso").value;
      const citta = document.getElementById("citta").value;
      let catastaleCitta = "";

      // Cerco il codice catastale della città nell'array dei codici
      cityCodes.forEach((el) => {
          if (el.nomeCitta == citta) {
              catastaleCitta = el.catastale;
          }
      });

      // Genero il codice fiscale e lo assegno all'input corrispondente
      const cf = generateCF(nome, cognome, dataNascita, sesso, catastaleCitta);
      cfInput.value = cf;
  });

  // Funzione per generare il codice fiscale
  function generateCF(nome, cognome, dataNascita, sesso, citta) {
      const months = {
          0: "A",
          1: "B",
          2: "C",
          3: "D",
          4: "E",
          5: "H",
          6: "L",
          7: "M",
          8: "P",
          9: "R",
          10: "S",
          11: "T",
      };

        // Estraggo il codice del cognome
  const surnameCode = getSurnameCode(cognome);
  // Estraggo il codice del nome
  const nameCode = getNameCode(nome);
  // Estraggo l'anno di nascita (ultime due cifre)
  const birthYear = dataNascita.substring(2, 4);
  const birthDate = new Date(dataNascita);
  // Estraggo il mese di nascita
  const birthMonth = months[birthDate.getMonth()];

  let birthDay;
  if (sesso === "F" && birthDate.getDate() < 10) {
      birthDay = birthDate.getDate() + 40;
  } else {
      birthDay = birthDate.getDate();
  }
  

  const birthDayStr = birthDay.toString().padStart(2, "0");   //padStart "allunga" la stringa, tipo ho 2 e faccio padStart (2,"0") diventa 002
  const cityCode = citta;
  // Concateno le varie parti del codice fiscale
  const partialCode =
      surnameCode + nameCode + birthYear + birthMonth + birthDayStr + cityCode;
  // Calcolo il carattere di controllo
  const controlChar = getControlCharacter(partialCode);

  return partialCode + controlChar; // Ritorno il codice fiscale completo
}

  // Funzione per ottenere il codice del cognome
  function getSurnameCode(surname) {
      // Estraggo le consonanti dal cognome
      const consonants = surname.replace(/[^BCDFGHJKLMNPQRSTVWXYZ]/gi, "");
      // Estraggo le vocali dal cognome
      const vowels = surname.replace(/[^AEIOU]/gi, "");
      // Concateno consonanti e vocali e prendo i primi tre caratteri, riempiendo con 'X' se necessario
      const surnameCode = (consonants + vowels + "XXX")
          .substring(0, 3)
          .toUpperCase();
      return surnameCode;
  }

  // Funzione per ottenere il codice del nome
  function getNameCode(name) {
      // Estraggo le consonanti dal nome
      const consonants = name.replace(/[^BCDFGHJKLMNPQRSTVWXYZ]/gi, "");
      // Estraggo le vocali dal nome
      const vowels = name.replace(/[^AEIOU]/gi, "");
      // Se ci sono almeno 4 consonanti, prendo la prima, la terza e la quarta
      // Altrimenti, concateno consonanti e vocali e prendo i primi tre caratteri, riempiendo con 'X' se necessario
      let nameCode =
          consonants.length >= 4
              ? consonants[0] + consonants[2] + consonants[3]
              : (consonants + vowels + "XXX").substring(0, 3);
      return nameCode.toUpperCase();
  }

  // Funzione per calcolare il carattere di controllo
  function getControlCharacter(code) {
      const charValues = {
          0: 1, 1: 0, 2: 5, 3: 7, 4: 9, 5: 13, 6: 15, 7: 17, 8: 19, 9: 21,
          A: 1, B: 0, C: 5, D: 7, E: 9, F: 13, G: 15, H: 17, I: 19, J: 21,
          K: 2, L: 4, M: 18, N: 20, O: 11, P: 3, Q: 6, R: 8, S: 12, T: 14,
          U: 16, V: 10, W: 22, X: 25, Y: 24, Z: 23,
      };
  
      // Calcolo la somma dei valori dei caratteri in posizione dispari
      let oddSum = 0;
      for (let i = 0; i < code.length; i += 2) {
          oddSum += charValues[code[i]];
      }
  
      // Calcolo la somma dei valori dei caratteri in posizione pari
      let evenSum = 0;
      for (let i = 1; i < code.length; i += 2) {
          evenSum += charValues[code[i]];
      }
  
      // Calcolo il totale e il carattere di controllo
      const total = oddSum + evenSum;
      const controlChar = String.fromCharCode((total % 26) + 65);
      return controlChar;
  }
  
  
  
  // Funzione di validazione per i campi del form
  function validazione(field) {
      const value = field.value;
      const id = field.id;
      let isValid = false;
  
      if (value === "") {
          showValidationMessage(field, false, "Questo campo non può essere vuoto."); // Mostro un messaggio di errore se il campo è vuoto
          return false;
      }
      switch (id) {
          case 'nome':
          case 'cognome':
              // Validazione per nome e cognome (minimo 3 caratteri e solo lettere)
              isValid = value.length >= 3 && /^[a-zA-Z]+$/.test(value);
              showValidationMessage(field, isValid, 'Il ' + id + ' non è valido');
              break;
          case 'citta':
              // Validazione per città (deve essere selezionata)
              isValid = value !== '';
              showValidationMessage(field, isValid, 'Seleziona una città.');
              break;
          case 'sesso':
              // Validazione per genere (deve essere selezionato)
              isValid = value !== '';
              showValidationMessage(field,isValid, 'Seleziona il sesso.');
              break;
          case "dataNascita":
              // Validazione per data di nascita
              isValid = validaDataNascita(value);
              showValidationMessage(field, isValid, "Data non valida.");
              break;
          case 'username':
              // Validazione per username (6-10 caratteri, 1 minuscolo, 1 maiuscolo, 2 numeri e 2 caratteri speciali)
              isValid = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d.*\d)(?=.*[?@_-]).{6,10}$/.test(value);
              showValidationMessage(field, isValid, 'Username deve avere tra 6 e 10 caratteri, almeno 1 minuscolo, 1 maiuscolo, 2 numeri e 2 caratteri speciali tra ?@_-.');
              break;
          case 'email':
              // Validazione per email (formato email)
              isValid = /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(value);
              showValidationMessage(field, isValid, 'Inserisci un\'email valida. Deve contenere una @');
              break;
          case 'password':
              // Validazione per password (6-10 caratteri, 1 minuscolo, 1 maiuscolo, 2 numeri e 2 caratteri speciali)
              isValid = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d.*\d)(?=.*[?@_-]).{6,10}$/.test(value);
              showValidationMessage(field, isValid, 'Password deve avere tra 6 e 10 caratteri, almeno 1 minuscolo, 1 maiuscolo, 2 numeri e 2 caratteri speciali tra ?@_-.');
              break;
          case 'confirmPassword':
              // Validazione per conferma password (deve corrispondere alla password)
              isValid = value === document.getElementById('password').value;
              showValidationMessage(field, isValid, 'Le password non corrispondono.');
              break;
      }
  
      return isValid; // Ritorno true se il campo è valido, altrimenti false
  }
  
  // Funzione per validare la data di nascita
  function validaDataNascita(value) {
    const date = new Date(value);
    const oggi = new Date();
    const maggioreEta = new Date(oggi.getFullYear() - 18, oggi.getMonth(), oggi.getDate() + 1);
    const annoMinimo = new Date(1920, 1, 1);
    return date < maggioreEta && date >= annoMinimo

}
  
  // Funzione per mostrare il messaggio di validazione
  function showValidationMessage(field, isValid, errorMessage) {
      const errorSpan = document.getElementById(field.id + 'Error');
      if (!isValid) {
          field.classList.add('error');
          field.classList.remove('success');
          errorSpan.textContent = errorMessage;
          errorSpan.classList.add('error-message');
          errorSpan.classList.remove('success-message');
      } else {
          field.classList.add('success');
          field.classList.remove('error');
          errorSpan.textContent = 'Valido';
          errorSpan.classList.add('success-message');
          errorSpan.classList.remove('error-message');
      }
  }
  
  // Funzionalità per mostrare/nascondere la password
  const passwordField = document.getElementById("password");
  const togglePassword = document.querySelector(".password-toggle-icon i");
  
  togglePassword.addEventListener("click", function () {
      if (passwordField.type === "password") {
          passwordField.type = "text";
          togglePassword.classList.remove("fa-eye");
          togglePassword.classList.add("fa-eye-slash");
      } else {
          passwordField.type = "password";
          togglePassword.classList.remove("fa-eye-slash");
          togglePassword.classList.add("fa-eye");
      }
  });

  // Event listener per tornare alla home page
  document.getElementById("tornaIndietro").addEventListener("click", function() {
      window.location.href = "homePage.html";
  });

  // Seleziona gli input password e conferma password
const passwordInput = document.getElementById("password");
const confirmPasswordInput = document.getElementById("confirmPassword");

// Aggiungi un event listener per impedire il copia-incolla nella conferma della password
confirmPasswordInput.addEventListener("copy", function (event) {
  event.preventDefault();
  alert("Copiare e incollare non consentiti nella conferma della password.");
});

confirmPasswordInput.addEventListener("paste", function (event) {
  event.preventDefault();
  alert("Copiare e incollare non consentiti nella conferma della password.");
});

// Aggiungi anche event listener per impedire il copia-incolla nella password
passwordInput.addEventListener("copy", function (event) {
  event.preventDefault();
  alert("Copiare e incollare non consentiti nella password.");
});

passwordInput.addEventListener("paste", function (event) {
  event.preventDefault();
  alert("Copiare e incollare non consentiti nella password.");
});

})

function clearAllValidationMessages() {
    document.querySelectorAll("input ,select").forEach(function (field) {
        const errorSpan = document.getElementById(field.id + "Error");
        if (errorSpan) {
            field.classList.remove("error", "success");
            errorSpan.textContent = "";
            errorSpan.classList.remove("error-message", "success-message");
        }
    });
}
/**
 * 
 */