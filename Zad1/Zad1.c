#include <stdio.h>
#include <stdlib.h>
// funkcja
char szyfr(char znak, int key){
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
        znak = NULL;
        return znak;
      }
    }
  }
}

int main(){
  char znak;
  //dotyczy pliku z tekstem zaszyfrowanym
  FILE *crypto;
  if((crypto=fopen("crypto.txt", "w"))==NULL) {
   printf ("Nie mogę otworzyć pliku crypto.txt do zapisu!\n");
   exit(1);
   }
   //dotyczy pliku z tekstem odszyfrowanym
   FILE *decrypt;
   if((decrypt=fopen("decrypt.txt", "w"))==NULL) {
    printf ("Nie mogę otworzyć pliku decrypt.txt do zapisu!\n");
    exit(1);
    }
  //dotyczy klucza
  int kluczyk;
  FILE *fkey;
  fkey=fopen("key.txt","r");
  fscanf(fkey,"%d",&kluczyk);
  fclose(fkey);
  //dotyczy pliku z tekstem orygnialnym
  FILE *plain;
  plain=fopen("plain.txt","r");
  while(plain != NULL){
    znak = fgetc(plain);
    if(feof(plain)){
      break;
    }
    printf("%c", znak);
    char wynik = szyfr(znak, kluczyk);
    fprintf (crypto, "%c", wynik);
  }
  fclose (crypto);
  fclose(plain);
  return 0;
}
