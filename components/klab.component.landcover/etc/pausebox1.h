//---------------------------------------------------------------------------

#ifndef pausebox1H
#define pausebox1H
//---------------------------------------------------------------------------
#include <Classes.hpp>
#include <Controls.hpp>
#include <StdCtrls.hpp>
#include <Forms.hpp>
#include "finish.h"
#include <ExtCtrls.hpp>
//---------------------------------------------------------------------------
class Tpausebox : public Tfinishbox
{
__published:	// IDE-managed Components
private:	// User declarations
public:		// User declarations
        __fastcall Tpausebox(TComponent* Owner);
};
//---------------------------------------------------------------------------
extern PACKAGE Tpausebox *pausebox;
//---------------------------------------------------------------------------
#endif
