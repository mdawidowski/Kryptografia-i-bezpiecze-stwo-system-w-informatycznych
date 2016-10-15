#include <stdio.h>
#include <stdlib.h>

char compare(char znak){
  int x,sum;
  int key=1;
  char a[25]={'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','r','s','t','u','v','w','x','y','z'};
  for(x=0; x<=25; x++){
    if(a[x]==znak){
      sum=x+key;
      znak=a[sum];
      return znak;
      }
    }
}
int szyfr(){

}

int main(){
  char znak;
  FILE *plain;
  plain=fopen("plain.txt","r");
//  while(plain != NULL){
    znak = fgetc(plain);
  //  if(feof(plain)){
      //break;
//    }
  printf("%c \n", znak);
  char wynik = compare(znak);
  printf("%c\n", wynik);
		//	}
  fclose(plain);
  return 0;
}
