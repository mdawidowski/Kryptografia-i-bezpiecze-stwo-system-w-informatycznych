#include <stdio.h>
#include <stdlib.h>
//////////////////////////////////////////////////////////////
//////////////////////funkcje odczytujące/////////////////////
//////////////////////////////////////////////////////////////
// char odczytzaszyfrowany(){
//   FILE *crypto;
//   if((crypto=fopen("crypto.txt", "w"))==NULL) {
//    printf ("Nie mogę otworzyć pliku crypto.txt do zapisu!\n");
//    exit(1);
//    }
//   while(crypto != NULL){
//    znak = fgetc(crypto);
//     if(feof(crypto)){
//      break;
//    }
//  }
// }
//////////////////////////////////////////////////////////////
///////////////////////funkcje szyfrujące/////////////////////
//////////////////////////////////////////////////////////////
int szyfrujcezar(char znak, int key){
  int x,sum,z;
  char a[25]={'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','r','s','t','u','v','w','x','y','z'};
  char d[25]={'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','R','S','T','U','V','W','X','Y','Z'};

  for(x=0; x<=25; x++){
    if (znak >= 'A' && znak <= 'Z'){
      for(z=0; z<=25; z++){
        a[z]=d[z];
      }
    }
    if(znak == '\n'){
      znak = '\n';
      return znak;
    }
    if(znak==a[x]){
      sum=x+key;
      if(sum>25){
        znak=a[sum-25];
        return znak;
      } else if(sum<26){
      znak=a[sum];
      return znak;
      }
      if(znak!=a[x]){
        break;
      }
    }
  }
}
// int odszyfrujcezar(char znak, int key){
//   int x,sum,z;
//   char a[25]={'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','r','s','t','u','v','w','x','y','z'};
//   char d[25]={'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','R','S','T','U','V','W','X','Y','Z'};
//
//   for(x=0; x<=25; x++){
//     if (znak >= 'A' && znak <= 'Z'){
//       for(z=0; z<=25; z++){
//         a[z]=d[z];
//       }
//     }
//     if(znak == '\n'){
//       znak = '\n';
//       fprintf (decrypt, "%c", znak);
//     }
//     if(znak==a[x]){
//       sum=x-key;
//       if(sum<0){
//         znak=a[sum+25];
//         fprintf (decrypt, "%c", znak);
//       } else if(sum>0 && sum<26){
//         znak=a[sum];
//         fprintf (decrypt, "%c", znak);
//       }
//     }
//   }
// }
//////////////////////////////////////////////////////////////
///////////////////////funkcje wykonujące/////////////////////
//////////////////////////////////////////////////////////////
int wykonuj(int parametr){
  int kluczyk, kluczyk1, kluczyk2;
  char znak,wynik;
  FILE *fkey;
  FILE *plain;
  FILE *crypto;

  switch (parametr) {

    case 1:     //szyfruj Cezar
      //odczyt klucza
      fkey=fopen("key.txt","r");
      fscanf(fkey,"%d",&kluczyk);
      if(kluczyk<-25 || kluczyk>25){
        printf("Błędny klucz. Do poprawy\n");
        exit(1);
      }
      //plik z szyfrem
      if((crypto=fopen("crypto.txt", "w"))==NULL) {
       printf ("Nie mogę otworzyć pliku crypto.txt do zapisu!\n");
       exit(1);
       }
      //odczyt tekstu jawnego i szyfrowanie
      plain=fopen("plain.txt","r");
      while(plain != NULL){
        znak = fgetc(plain);
        if(feof(plain)){
          break;
        }
        char wynik = szyfrujcezar(znak, kluczyk);
        fprintf (crypto, "%c", wynik);
    }

    // case 2:     //odszyfruj Cezar
    // case 3:     //kryptoanaliza z jawnym Cezar
    // case 4:     //kryptoanaliza w oparciu o kryptogram Cezar

    case 5:     //szyfruj afiniczny
      //odczyt kluczy
      fkey=fopen("key.txt","r");
      fscanf(fkey,"%d%d",&kluczyk1, &kluczyk2);
      if(kluczyk1<-25 || kluczyk1>25){
        printf("Błędny klucz. Do poprawy\n");
        exit(1);
      }
      //odczyt tekstu jawnego
      plain=fopen("plain.txt","r");
      while(plain != NULL){
        znak = fgetc(plain);
        if(feof(plain)){
          break;
        }

      }
    // case 6:     //odszyfruj afiniczny
    // case 7:     //kryptoanaliza z jawnym afiniczny
    // case 8:     //kryptoanaliza w oparciu o kryptogram afiniczny
  }
}
int main(int argc, char * argv[]){
  //Cezar
  if ( !strcmp(argv[1], "-c") && !strcmp(argv[2], "-e") ){
    wykonuj(1);
  }
  if ( !strcmp(argv[1], "-c") && !strcmp(argv[2], "-d") ){
    wykonuj(2);
  }
  if ( !strcmp(argv[1], "-c") && !strcmp(argv[2], "-j") ){
    wykonuj(3);
  }
  if ( !strcmp(argv[1], "-c") && !strcmp(argv[2], "-k") ){
    wykonuj(4);
  }

  //Afiniczny
  if ( !strcmp(argv[1], "-a") && !strcmp(argv[2], "-e") ){
    wykonuj(5);
  }
  if ( !strcmp(argv[1], "-a") && !strcmp(argv[2], "-d") ){
    wykonuj(6);
  }
  if ( !strcmp(argv[1], "-a") && !strcmp(argv[2], "-j") ){
    wykonuj(7);
  }
  if ( !strcmp(argv[1], "-a") && !strcmp(argv[2], "-k") ){
    wykonuj(8);
  }
}
