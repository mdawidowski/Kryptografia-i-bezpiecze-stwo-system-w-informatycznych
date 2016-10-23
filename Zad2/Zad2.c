#include <stdio.h>
#include <stdlib.h>
char przygotuj(){
  char a[25]={'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','r','s','t','u','v','w','x','y','z'};
  char d[25]={'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','R','S','T','U','V','W','X','Y','Z'};
  FILE *original;
  char znak,poprawiony;
  int i;
  original=fopen("orig.txt","r");
  while(original != NULL){
    znak = fgetc(original);
    for (i=0;i<26;i++){
      if(znak == d[i]){
        znak == a[i];
      }
    }
    if(feof(original)){
      break;
    }
    poprawiony = szyfrujcezar(znak, kluczyk);
    fprintf (crypto, "%c", poprawiony);
  }
}
int int main() {
  return 0;
}
